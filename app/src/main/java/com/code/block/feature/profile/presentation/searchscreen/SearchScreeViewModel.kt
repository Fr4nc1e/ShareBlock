package com.code.block.feature.profile.presentation.searchscreen

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.code.block.core.domain.resource.Resource
import com.code.block.core.domain.state.TextFieldState
import com.code.block.core.util.ui.UiEvent
import com.code.block.core.util.ui.UiText
import com.code.block.usecase.profile.ProfileUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

@HiltViewModel
class SearchScreeViewModel @Inject constructor(
    private val profileUseCases: ProfileUseCases
) : ViewModel() {

    private val _searchState = mutableStateOf(SearchState())
    val searchState: State<SearchState> = _searchState

    private val _searchTextFiledState = mutableStateOf(TextFieldState())
    val searchTextFieldState: State<TextFieldState> = _searchTextFiledState

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    private var searchJob: Job? = null

    fun onEvent(event: SearchEvent) {
        when (event) {
            is SearchEvent.EnteredSearchText -> {
                _searchTextFiledState.value = _searchTextFiledState.value.copy(
                    text = event.searchText
                )
            }
            is SearchEvent.ClearSearchText -> {
                _searchTextFiledState.value = TextFieldState()
            }
            is SearchEvent.Search -> {
                searchUser(searchTextFieldState.value.text)
            }
            is SearchEvent.FollowMotion -> {
                followUser(event.userId)
            }
        }
    }

    private fun followUser(userId: String) {
        viewModelScope.launch {
            val isFollowing = searchState.value.userItems.find {
                it.userId == userId
            }?.isFollowing == true

            _searchState.value = _searchState.value.copy(
                userItems = searchState.value.userItems.map {
                    if (it.userId == userId) {
                        it.copy(isFollowing = !it.isFollowing)
                    } else {
                        it
                    }
                }
            )

            profileUseCases.followUserUseCase(
                userId = userId,
                isFollowing = isFollowing
            ).apply {
                when (this) {
                    is Resource.Success -> Unit
                    is Resource.Error -> {
                        _searchState.value = searchState.value.copy(
                            userItems = searchState.value.userItems.map {
                                if (it.userId == userId) {
                                    it.copy(isFollowing = isFollowing)
                                } else {
                                    it
                                }
                            }
                        )
                        _eventFlow.emit(
                            UiEvent.SnackBarEvent(uiText = this.uiText ?: UiText.unknownError())
                        )
                    }
                }
            }
        }
    }

    private fun searchUser(query: String) {
        searchJob?.cancel()
        searchJob = viewModelScope.launch {
            delay(1000L)
            _searchState.value = _searchState.value.copy(
                isLoading = true
            )
            profileUseCases.searchUseCase(query)
                .apply {
                    when (this) {
                        is Resource.Success -> {
                            _searchState.value = _searchState.value.copy(
                                userItems = this.data ?: emptyList(),
                                isLoading = false
                            )
                        }
                        is Resource.Error -> {
                            _searchTextFiledState.value = _searchTextFiledState.value.copy(
                                error = SearchError(
                                    message = this.uiText ?: UiText.unknownError()
                                )
                            )
                            _searchState.value = _searchState.value.copy(
                                isLoading = false
                            )
                        }
                    }
                }
        }
    }
}
