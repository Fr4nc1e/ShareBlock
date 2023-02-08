package com.code.block.feature.post.presentation.homescreen

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.code.block.R
import com.code.block.core.domain.model.Post
import com.code.block.core.domain.resource.Resource
import com.code.block.core.domain.state.PageState
import com.code.block.core.domain.type.ParentType
import com.code.block.core.util.ui.UiEvent
import com.code.block.core.util.ui.UiText
import com.code.block.core.util.ui.liker.Liker
import com.code.block.core.util.ui.paging.PaginatorImpl
import com.code.block.usecase.post.PostUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.* // ktlint-disable no-wildcard-imports
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val postUseCases: PostUseCases,
    private val liker: Liker,
) : ViewModel() {
    private val _state = mutableStateOf(HomeState())
    val state: State<HomeState> = _state

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    private val _pagingState = mutableStateOf<PageState<Post>>(PageState())
    val pagingState: State<PageState<Post>> = _pagingState

    private val _isRefreshing = MutableStateFlow(false)
    val isRefreshing: StateFlow<Boolean>
        get() = _isRefreshing.asStateFlow()

    private val paginator = PaginatorImpl(
        onLoadUpdated = { isLoading ->
            _pagingState.value = _pagingState.value.copy(
                isLoading = isLoading,
            )
        },
        onRequest = { page ->
            postUseCases.getPostsForFollowUseCase(page)
        },
        onSuccess = { posts ->
            _pagingState.value = _pagingState.value.copy(
                items = pagingState.value.items + posts,
                endReached = posts.isEmpty(),
                isLoading = false,
            )
        },
        onError = { uiText ->
            _eventFlow.emit(UiEvent.SnackBarEvent(uiText = uiText))
        },
    )

    init {
        loadNextPosts()
    }

    fun onEvent(event: HomeEvent) {
        when (event) {
            is HomeEvent.LikedParent -> {
                likeParent(parentId = event.postId)
            }
            HomeEvent.Refresh -> {
                refresh()
            }
            is HomeEvent.DeletePost -> {
                deletePost(event.post.id)
            }
        }
    }

    fun loadNextPosts() {
        viewModelScope.launch {
            paginator.loadNextItems()
        }
    }

    private fun deletePost(postId: String) {
        viewModelScope.launch {
            when (val result = postUseCases.deletePost(postId)) {
                is Resource.Success -> {
                    _pagingState.value = pagingState.value.copy(
                        items = pagingState.value.items.filter {
                            it.id != postId
                        },
                    )
                    _eventFlow.emit(
                        UiEvent.SnackBarEvent(
                            UiText.StringResource(
                                R.string.successfully_deleted_post,
                            ),
                        ),
                    )
                }
                is Resource.Error -> {
                    _eventFlow.emit(
                        UiEvent.SnackBarEvent(result.uiText ?: UiText.unknownError()),
                    )
                }
            }
        }
    }

    private fun likeParent(
        parentId: String,
    ) {
        viewModelScope.launch {
            liker.clickLike(
                posts = pagingState.value.items,
                parentId = parentId,
                onRequest = { isLiked ->
                    postUseCases.likeParentUseCase(
                        parentId = parentId,
                        parentType = ParentType.Post.type,
                        isLiked = isLiked,
                    )
                },
                onStateUpdate = { posts ->
                    _pagingState.value = _pagingState.value.copy(
                        items = posts,
                    )
                },
            )
        }
    }

    private fun refresh() {
        viewModelScope.launch {
            _isRefreshing.emit(true)
            delay(500L)
            paginator.page = 0
            _pagingState.value = _pagingState.value.copy(
                items = emptyList(),
            )
            loadNextPosts()
            _isRefreshing.emit(false)
        }
    }
}
