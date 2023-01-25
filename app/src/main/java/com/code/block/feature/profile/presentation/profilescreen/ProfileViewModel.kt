package com.code.block.feature.profile.presentation.profilescreen

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.code.block.core.domain.model.Post
import com.code.block.core.domain.resource.Resource
import com.code.block.core.domain.state.PageState
import com.code.block.core.domain.type.ParentType
import com.code.block.core.usecase.GetOwnUserIdUseCase
import com.code.block.core.util.ui.UiEvent
import com.code.block.core.util.ui.UiText
import com.code.block.core.util.ui.liker.Liker
import com.code.block.core.util.ui.paging.PaginatorImpl
import com.code.block.usecase.post.PostUseCases
import com.code.block.usecase.profile.ProfileUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val profileUseCases: ProfileUseCases,
    private val postUseCases: PostUseCases,
    private val getOwnUserIdUseCase: GetOwnUserIdUseCase,
    private val savedStateHandle: SavedStateHandle,
    private val liker: Liker
) : ViewModel() {

    private val _state = mutableStateOf(ProfileState())
    val state: State<ProfileState> = _state

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    private val _ownPagingState = mutableStateOf<PageState<Post>>(PageState())
    val ownPagingState: State<PageState<Post>> = _ownPagingState

    private val _likePagingState = mutableStateOf<PageState<Post>>(PageState())
    val likePagingState: State<PageState<Post>> = _likePagingState

    private val ownPaginator = PaginatorImpl(
        onLoadUpdated = { isLoading ->
            _ownPagingState.value = ownPagingState.value.copy(
                isLoading = isLoading
            )
        },
        onRequest = { page ->
            val userId = savedStateHandle.get<String>("userId") ?: getOwnUserIdUseCase()
            profileUseCases.getOwnPostsProfileUseCase(
                userId = userId,
                page = page
            )
        },
        onSuccess = { posts ->
            _ownPagingState.value = ownPagingState.value.copy(
                items = ownPagingState.value.items + posts,
                endReached = posts.isEmpty(),
                isLoading = false
            )
        },
        onError = { uiText ->
            _eventFlow.emit(UiEvent.SnackBarEvent(uiText))
        }
    )

    private val likePaginator = PaginatorImpl(
        onLoadUpdated = { isLoading ->
            _likePagingState.value = likePagingState.value.copy(
                isLoading = isLoading
            )
        },
        onRequest = { page ->
            val userId = savedStateHandle.get<String>("userId") ?: getOwnUserIdUseCase()
            profileUseCases.getLikedPostsProfileUseCase(
                userId = userId,
                page = page
            )
        },
        onSuccess = { posts ->
            _likePagingState.value = likePagingState.value.copy(
                items = likePagingState.value.items + posts,
                endReached = posts.isEmpty(),
                isLoading = false
            )
        },
        onError = { uiText ->
            _eventFlow.emit(UiEvent.SnackBarEvent(uiText))
        }
    )

    init {
        loadOwnPosts()
        loadLikedPosts()
    }

    fun loadOwnPosts() {
        viewModelScope.launch {
            ownPaginator.loadNextItems()
        }
    }

    fun loadLikedPosts() {
        viewModelScope.launch {
            likePaginator.loadNextItems()
        }
    }

    fun onEvent(event: ProfileEvent) {
        when (event) {
            is ProfileEvent.GetProfile -> {
                getProfile(userId = event.userId)
            }
            is ProfileEvent.OwnPageLikePost -> {
                viewModelScope.launch {
                    ownLikeParent(
                        parentId = event.postId
                    )
                }
            }
            is ProfileEvent.LikePageLikePost -> {
                viewModelScope.launch {
                    likeLikeParent(
                        parentId = event.postId
                    )
                }
            }
        }
    }

    private fun likeLikeParent(
        parentId: String
    ) {
        viewModelScope.launch {
            liker.clickLike(
                posts = likePagingState.value.items,
                parentId = parentId,
                onRequest = { isLiked ->
                    postUseCases.likeParentUseCase(
                        parentId = parentId,
                        parentType = ParentType.Post.type,
                        isLiked = isLiked
                    )
                },
                onStateUpdate = { posts ->
                    _likePagingState.value = likePagingState.value.copy(
                        items = posts
                    )
                }
            )
        }
    }

    private fun ownLikeParent(
        parentId: String
    ) {
        viewModelScope.launch {
            liker.clickLike(
                posts = ownPagingState.value.items,
                parentId = parentId,
                onRequest = { isLiked ->
                    postUseCases.likeParentUseCase(
                        parentId = parentId,
                        parentType = ParentType.Post.type,
                        isLiked = isLiked
                    )
                },
                onStateUpdate = { posts ->
                    _ownPagingState.value = ownPagingState.value.copy(
                        items = posts
                    )
                }
            )
        }
    }

    fun getProfile(userId: String?) {
        viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true)
            profileUseCases.getProfileUseCase(
                userId ?: getOwnUserIdUseCase()
            ).apply {
                when (this) {
                    is Resource.Success -> {
                        _state.value = _state.value.copy(
                            profile = this.data,
                            isLoading = false
                        )
                    }
                    is Resource.Error -> {
                        _state.value = _state.value.copy(isLoading = false)
                        _eventFlow.emit(
                            UiEvent.SnackBarEvent(this.uiText ?: UiText.unknownError())
                        )
                    }
                }
            }
        }
    }
}
