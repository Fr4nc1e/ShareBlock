package com.code.block.feature.post.presentation.homescreen

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.code.block.feature.post.domain.usecase.PostUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    postUseCases: PostUseCases
) : ViewModel() {
    private val _state = mutableStateOf(HomeState())
    val state: State<HomeState> = _state

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
        }
    }

    fun refresh(
        onRefresh: () -> Unit = {}
    ) {
        viewModelScope.launch {
            _isRefreshing.emit(true)
            delay(500)
            onRefresh()
            _isRefreshing.emit(false)
        }
    }
}
