package com.code.block.feature.post.presentation.homescreen

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.code.block.feature.post.domain.usecase.PostUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    postUseCases: PostUseCases
) : ViewModel() {
    private val _state = mutableStateOf(HomeState())
    val state: State<HomeState> = _state

    val posts = postUseCases.getPostsForFollowUseCase()
        .cachedIn(viewModelScope)

    fun onEvent(event: HomeEvent) {
        when (event) {
            HomeEvent.LoadPage -> {
                _state.value = state.value.copy(
                    isLoadingFirstTime = false,
                    isLoadingNewPosts = false
                )
            }
            HomeEvent.LoadPosts -> {
                _state.value = state.value.copy(
                    isLoadingNewPosts = true
                )
            }
        }
    }
}
