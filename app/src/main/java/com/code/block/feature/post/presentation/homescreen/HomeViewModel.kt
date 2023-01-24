package com.code.block.feature.post.presentation.homescreen

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.code.block.core.domain.util.ParentType
import com.code.block.core.domain.util.Resource
import com.code.block.core.util.UiEvent
import com.code.block.feature.post.domain.usecase.PostUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.* // ktlint-disable no-wildcard-imports
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val postUseCases: PostUseCases
) : ViewModel() {
    private val _state = mutableStateOf(HomeState())
    val state: State<HomeState> = _state

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    val posts = postUseCases.getPostsForFollowUseCase()
        .cachedIn(viewModelScope)

    private val _isRefreshing = MutableStateFlow(false)
    val isRefreshing: StateFlow<Boolean>
        get() = _isRefreshing.asStateFlow()

    fun onEvent(event: HomeEvent) {
        when (event) {
            HomeEvent.LoadPage -> {
                _state.value = _state.value.copy(
                    isLoadingFirstTime = false,
                    isLoadingNewPosts = false
                )
            }
            HomeEvent.LoadPosts -> {
                _state.value = _state.value.copy(
                    isLoadingNewPosts = true
                )
            }
            HomeEvent.Refresh -> {
                refresh()
            }
            is HomeEvent.LikedParent -> {
                likeParent(
                    parentId = event.postId,
                    isLiked = true
                )
            }
        }
    }

    private fun likeParent(
        parentId: String,
        isLiked: Boolean
    ) {
        viewModelScope.launch {
            val result = postUseCases.likeParentUseCase(
                parentId = parentId,
                parentType = ParentType.Post.type,
                isLiked = isLiked
            )
            when (result) {
                is Resource.Success -> {
                    _eventFlow.emit(UiEvent.OnLikeParent)
                }
                is Resource.Error -> {}
            }
        }
    }

    fun refresh(
        onRefresh: () -> Unit = {}
    ) {
        viewModelScope.launch {
            _isRefreshing.emit(true)
            delay(500L)
            onRefresh()
            _isRefreshing.emit(false)
        }
    }
}
