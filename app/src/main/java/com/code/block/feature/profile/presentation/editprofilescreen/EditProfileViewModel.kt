package com.code.block.feature.profile.presentation.editprofilescreen

import android.net.Uri
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.code.block.R
import com.code.block.core.domain.resource.Resource
import com.code.block.core.util.ui.UiEvent
import com.code.block.core.util.ui.UiText
import com.code.block.feature.profile.domain.model.UpdateProfileData
import com.code.block.feature.profile.domain.state.EditTextState
import com.code.block.feature.profile.presentation.profilescreen.ProfileState
import com.code.block.usecase.profile.ProfileUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EditProfileViewModel @Inject constructor(
    private val profileUseCases: ProfileUseCases,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    private val _usernameState = mutableStateOf(EditTextState())
    val usernameState: State<EditTextState> = _usernameState

    private val _githubTextFieldState = mutableStateOf(EditTextState())
    val githubTextFieldState: State<EditTextState> = _githubTextFieldState

    private val _weChatTextFieldState = mutableStateOf(EditTextState())
    val weChatTextFieldState: State<EditTextState> = _weChatTextFieldState

    private val _qqTextFieldState = mutableStateOf(EditTextState())
    val qqTextFieldState: State<EditTextState> = _qqTextFieldState

    private val _bioState = mutableStateOf(EditTextState())
    val bioState: State<EditTextState> = _bioState

    private val _profileState = mutableStateOf(ProfileState())
    val profileState: State<ProfileState> = _profileState

    private val _bannerUri = mutableStateOf<Uri?>(null)
    val bannerUri: State<Uri?> = _bannerUri

    private val _profilePictureUri = mutableStateOf<Uri?>(null)
    val profilePictureUri: State<Uri?> = _profilePictureUri

    init {
        savedStateHandle.get<String>("userId")?.let { userId ->
            getProfile(userId)
        }
    }

    private fun getProfile(userId: String) {
        viewModelScope.launch {
            _profileState.value = profileState.value.copy(isLoading = true)
            when (val result = profileUseCases.getProfileUseCase(userId)) {
                is Resource.Success -> {
                    val profile = result.data ?: kotlin.run {
                        _eventFlow.emit(
                            UiEvent.SnackBarEvent(
                                UiText.StringResource(R.string.error_couldnt_load_profile),
                            ),
                        )
                        return@launch
                    }
                    _usernameState.value = usernameState.value.copy(
                        text = profile.username,
                    )
                    _githubTextFieldState.value = _githubTextFieldState.value.copy(
                        text = profile.gitHubUrl ?: "",
                    )
                    _weChatTextFieldState.value = _weChatTextFieldState.value.copy(
                        text = profile.weChatUrl ?: "",
                    )
                    _qqTextFieldState.value = _qqTextFieldState.value.copy(
                        text = profile.qqUrl ?: "",
                    )
                    _bioState.value = bioState.value.copy(
                        text = profile.bio,
                    )
                    _profileState.value = profileState.value.copy(
                        profile = profile,
                        isLoading = false,
                    )
                }
                is Resource.Error -> {
                    _eventFlow.emit(UiEvent.SnackBarEvent(result.uiText ?: UiText.unknownError()))
                    return@launch
                }
            }
        }
    }

    private fun updateProfile() {
        viewModelScope.launch {
            val result = profileUseCases.updateProfileUseCase(
                updateProfileData = UpdateProfileData(
                    username = usernameState.value.text,
                    bio = bioState.value.text,
                    gitHubUrl = githubTextFieldState.value.text,
                    weChatUrl = weChatTextFieldState.value.text,
                    qqUrl = qqTextFieldState.value.text,
                ),
                profilePictureUri = profilePictureUri.value,
                bannerUri = bannerUri.value,
            )
            when (result) {
                is Resource.Success -> {
                    _eventFlow.apply {
                        emit(UiEvent.SnackBarEvent(UiText.StringResource(R.string.updated_profile)))
                        emit(UiEvent.NavigateUp)
                    }
                }
                is Resource.Error -> {
                    _eventFlow.emit(UiEvent.SnackBarEvent(result.uiText ?: UiText.unknownError()))
                }
            }
        }
    }

    fun onEvent(event: EditProfileEvent) {
        when (event) {
            is EditProfileEvent.EnteredUsername -> {
                _usernameState.value = usernameState.value.copy(
                    text = event.username,
                )
            }
            is EditProfileEvent.EnteredGitHub -> {
                _githubTextFieldState.value = _githubTextFieldState.value.copy(
                    text = event.gitHub,
                )
            }
            is EditProfileEvent.EnteredWeChat -> {
                _weChatTextFieldState.value = _weChatTextFieldState.value.copy(
                    text = event.weChat,
                )
            }
            is EditProfileEvent.EnteredQq -> {
                _qqTextFieldState.value = _qqTextFieldState.value.copy(
                    text = event.qq,
                )
            }
            is EditProfileEvent.EnteredBio -> {
                _bioState.value = _bioState.value.copy(
                    text = event.bio,
                )
            }
            is EditProfileEvent.CropProfilePicture -> {
                _profilePictureUri.value = event.uri
            }
            is EditProfileEvent.CropBannerImage -> {
                _bannerUri.value = event.uri
            }
            EditProfileEvent.EditionCompleted -> {
                updateProfile()
            }
            EditProfileEvent.ClearBio -> {
                _bioState.value = EditTextState()
            }
            EditProfileEvent.ClearGitHub -> {
                _githubTextFieldState.value = EditTextState()
            }
            EditProfileEvent.ClearQq -> {
                _qqTextFieldState.value = EditTextState()
            }
            EditProfileEvent.ClearUsername -> {
                _usernameState.value = EditTextState()
            }
            EditProfileEvent.ClearWeChat -> {
                _weChatTextFieldState.value = EditTextState()
            }
        }
    }
}
