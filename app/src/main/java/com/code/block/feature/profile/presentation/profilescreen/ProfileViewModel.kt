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
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _state = mutableStateOf(ProfileState())
    val state: State<ProfileState> = _state

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    private var ownPage = 0
    private var likePage = 0

    private val _ownPagingState = mutableStateOf<PageState<Post>>(PageState())
    val ownPagingState: State<PageState<Post>> = _ownPagingState
    private val _likePagingState = mutableStateOf<PageState<Post>>(PageState())
    val likePagingState: State<PageState<Post>> = _likePagingState

    init {
        loadOwnPosts()
        loadLikedPosts()
    }

    fun loadOwnPosts() {
        viewModelScope.launch {
            _ownPagingState.value = ownPagingState.value.copy(
                isLoading = true
            )
            val userId = savedStateHandle.get<String>("userId") ?: getOwnUserIdUseCase()
            val result = profileUseCases.getOwnPostsProfileUseCase(
                userId = userId,
                page = ownPage
            )
            when (result) {
                is Resource.Success -> {
                    val posts = result.data ?: emptyList()
                    _ownPagingState.value = ownPagingState.value.copy(
                        items = ownPagingState.value.items + posts,
                        endReached = posts.isEmpty(),
                        isLoading = false
                    )
                    ownPage++
                }
                is Resource.Error -> {
                    _eventFlow.emit(
                        UiEvent.SnackBarEvent(result.uiText ?: UiText.unknownError())
                    )
                    _ownPagingState.value = ownPagingState.value.copy(
                        isLoading = false
                    )
                }
            }
        }
    }

    fun loadLikedPosts() {
        viewModelScope.launch {
            _likePagingState.value = likePagingState.value.copy(
                isLoading = true
            )
            val userId = savedStateHandle.get<String>("userId") ?: getOwnUserIdUseCase()
            val result = profileUseCases.getLikedPostsProfileUseCase(
                userId = userId,
                page = likePage
            )
            when (result) {
                is Resource.Success -> {
                    val posts = result.data ?: emptyList()
                    _likePagingState.value = likePagingState.value.copy(
                        items = likePagingState.value.items + posts,
                        endReached = posts.isEmpty(),
                        isLoading = false
                    )
                    likePage++
                }
                is Resource.Error -> {
                    _eventFlow.emit(
                        UiEvent.SnackBarEvent(result.uiText ?: UiText.unknownError())
                    )
                    _likePagingState.value = likePagingState.value.copy(
                        isLoading = false
                    )
                }
            }
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
            val post = likePagingState.value.items.find { it.id == parentId }
            val currentlyLiked = post?.isLiked == true
            val currentLikeCount = post?.likeCount ?: 0
            val newPosts = likePagingState.value.items.map { post1 ->
                if (post1.id == parentId) {
                    post1.copy(
                        isLiked = !post1.isLiked,
                        likeCount = if (currentlyLiked) {
                            post1.likeCount - 1
                        } else post1.likeCount + 1
                    )
                } else post1
            }
            val result = postUseCases.likeParentUseCase(
                parentId = parentId,
                parentType = ParentType.Post.type,
                isLiked = currentlyLiked
            )
            _likePagingState.value = likePagingState.value.copy(
                items = newPosts
            )
            when (result) {
                is Resource.Success -> {
                    _eventFlow.emit(UiEvent.OnLikeParent)
                    loadLikedPosts()
                }
                is Resource.Error -> {
                    val oldPosts = likePagingState.value.items.map { post2 ->
                        if (post2.id == parentId) {
                            post2.copy(
                                isLiked = currentlyLiked,
                                likeCount = currentLikeCount
                            )
                        } else post2
                    }
                    _likePagingState.value = likePagingState.value.copy(
                        items = oldPosts
                    )
                }
            }
        }
    }

    private fun ownLikeParent(
        parentId: String
    ) {
        viewModelScope.launch {
            val post = ownPagingState.value.items.find { it.id == parentId }
            val currentlyLiked = post?.isLiked == true
            val currentLikeCount = post?.likeCount ?: 0
            val newPosts = ownPagingState.value.items.map { post1 ->
                if (post1.id == parentId) {
                    post1.copy(
                        isLiked = !post1.isLiked,
                        likeCount = if (currentlyLiked) {
                            post1.likeCount - 1
                        } else post1.likeCount + 1
                    )
                } else post1
            }
            val result = postUseCases.likeParentUseCase(
                parentId = parentId,
                parentType = ParentType.Post.type,
                isLiked = currentlyLiked
            )
            _ownPagingState.value = ownPagingState.value.copy(
                items = newPosts
            )
            when (result) {
                is Resource.Success -> {
                    _eventFlow.emit(UiEvent.OnLikeParent)
                    loadLikedPosts()
                }
                is Resource.Error -> {
                    val oldPosts = ownPagingState.value.items.map { post2 ->
                        if (post2.id == parentId) {
                            post2.copy(
                                isLiked = currentlyLiked,
                                likeCount = currentLikeCount
                            )
                        } else post2
                    }
                    _ownPagingState.value = ownPagingState.value.copy(
                        items = oldPosts
                    )
                }
            }
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
