package com.code.block.feature.post.presentation.createpostscreen

import android.net.Uri
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.code.block.R
import com.code.block.core.domain.resource.Resource
import com.code.block.core.domain.state.TextFieldState
import com.code.block.core.usecase.GetOwnUserIdUseCase
import com.code.block.core.usecase.notification.NotificationUseCases
import com.code.block.core.util.UserInfoProvider
import com.code.block.core.util.ui.UiEvent
import com.code.block.core.util.ui.UiText
import com.code.block.feature.post.presentation.postdetailscreen.state.UserInfoState
import com.code.block.usecase.post.PostUseCases
import com.code.block.usecase.profile.ProfileUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CreatePostViewModel @Inject constructor(
    private val postUseCases: PostUseCases,
    private val profileUseCases: ProfileUseCases,
    private val notificationUseCases: NotificationUseCases,
    private val getOwnUserIdUseCase: GetOwnUserIdUseCase,
) : ViewModel() {

    private val _descriptionState = mutableStateOf(TextFieldState())
    val descriptionState: State<TextFieldState> = _descriptionState

    private val _chosenContentUri = mutableStateOf<Uri?>(null)
    val chosenContentUri: State<Uri?> = _chosenContentUri

    private val _isLoading = mutableStateOf(false)
    val isLoading: State<Boolean> = _isLoading

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    private val _userInfoState = mutableStateOf(UserInfoState())
    val userInfoState: State<UserInfoState> = _userInfoState

    init {
        getUserInfo()
    }

    fun onEvent(event: CreatePostEvent) {
        when (event) {
            is CreatePostEvent.EnteredDescription -> {
                _descriptionState.value = _descriptionState.value.copy(
                    text = event.description,
                )
            }
            is CreatePostEvent.InputContent -> {
                _chosenContentUri.value = event.contentUri
            }
            is CreatePostEvent.Post -> {
                chosenContentUri.value?.let {
                    viewModelScope.launch {
                        _isLoading.value = true
                        val result = postUseCases.createPostUseCase(
                            description = descriptionState.value.text,
                            contentUri = chosenContentUri.value,
                        )
                        when (result) {
                            is Resource.Success -> {
                                _eventFlow.emit(
                                    UiEvent.SnackBarEvent(
                                        uiText = UiText.StringResource(R.string.post_created),
                                    ),
                                )
                                notificationUseCases.sendPostNotificationUseCase(
                                    title = _userInfoState.value.username,
                                    description = _descriptionState.value.text,
                                ).also {
                                    when (it) {
                                        is Resource.Error -> {
                                            _eventFlow.emit(
                                                UiEvent.SnackBarEvent(
                                                    result.uiText ?: UiText.unknownError(),
                                                ),
                                            )
                                        }
                                        is Resource.Success -> Unit
                                    }
                                }
                                delay(1_000L)
                                _eventFlow.emit(UiEvent.NavigateUp)
                            }
                            is Resource.Error -> {
                                _eventFlow.emit(
                                    UiEvent.SnackBarEvent(
                                        result.uiText ?: UiText.unknownError(),
                                    ),
                                )
                            }
                        }
                        _isLoading.value = false
                    }
                }
            }
        }
    }

    private fun getUserInfo() {
        viewModelScope.launch {
            UserInfoProvider(profileUseCases = profileUseCases).provideUserInfo(
                userId = getOwnUserIdUseCase(),
                userInfoState = _userInfoState,
            )
        }
    }
}
