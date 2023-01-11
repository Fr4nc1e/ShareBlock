package com.code.block.feature.profile.presentation.editprofilescreen

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.code.block.core.utils.Constants
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class EditProfileViewModel @Inject constructor() : ViewModel() {

    private val _state = mutableStateOf(EditProfileState())
    val state: State<EditProfileState> = _state

    fun onEvent(event: EditProfileEvent) {
        when (event) {
            is EditProfileEvent.EnteredUsername -> {
                _state.value = _state.value.copy(
                    usernameText = event.username
                )
            }
            is EditProfileEvent.EnteredQq -> {
                _state.value = _state.value.copy(
                    qqText = event.qq
                )
            }
            is EditProfileEvent.EnteredWeChat -> {
                _state.value = _state.value.copy(
                    weChatText = event.weChat
                )
            }
            is EditProfileEvent.EnteredGitHub -> {
                _state.value = _state.value.copy(
                    gitHubText = event.gitHub
                )
            }
            is EditProfileEvent.EnteredBio -> {
                _state.value = _state.value.copy(
                    bioText = event.bio
                )
            }
            is EditProfileEvent.ClearUsername -> {
                _state.value = _state.value.copy(
                    usernameText = ""
                )
            }
            is EditProfileEvent.ClearWeChat -> {
                _state.value = _state.value.copy(
                    weChatText = ""
                )
            }
            is EditProfileEvent.ClearQq -> {
                _state.value = _state.value.copy(
                    qqText = ""
                )
            }
            is EditProfileEvent.ClearGitHub -> {
                _state.value = _state.value.copy(
                    gitHubText = ""
                )
            }
            is EditProfileEvent.ClearBio -> {
                _state.value = _state.value.copy(
                    bioText = ""
                )
            }
            is EditProfileEvent.EditionCompleted -> {
                validateUsername(username = state.value.usernameText)
                validateQq(qq = state.value.qqText)
                validateWeChat(weChat = state.value.weChatText)
                validateGituHub(gitHub = state.value.gitHubText)
                validateBio(bio = state.value.bioText)
            }
        }
    }

    private fun validateUsername(username: String) {
        val trimmedUsername = username.trim()

        if (trimmedUsername.isBlank()) {
            _state.value = _state.value.copy(
                usernameError = EditProfileState.UsernameError.FieldEmpty
            )
            return
        }
        if (trimmedUsername.length < Constants.MIN_USERNAME_LENGTH) {
            _state.value = _state.value.copy(
                usernameError = EditProfileState.UsernameError.InputTooShort
            )
            return
        }

        _state.value = _state.value.copy(
            usernameError = null
        )
    }

    private fun validateQq(qq: String) {
        val trimmedQq = qq.trim()

        if (trimmedQq.isBlank()) {
            _state.value = _state.value.copy(
                qqError = EditProfileState.QqError.FieldEmpty
            )
            return
        }

        _state.value = _state.value.copy(
            qqError = null
        )
    }

    private fun validateWeChat(weChat: String) {
        val trimmedWeChat = weChat.trim()

        if (trimmedWeChat.isBlank()) {
            _state.value = _state.value.copy(
                weChatError = EditProfileState.WeChatError.FieldEmpty
            )
            return
        }

        _state.value = _state.value.copy(
            weChatError = null
        )
    }

    private fun validateGituHub(gitHub: String) {
        val trimmedGitHub = gitHub.trim()

        if (trimmedGitHub.isBlank()) {
            _state.value = _state.value.copy(
                gitHubError = EditProfileState.GitHubError.FieldEmpty
            )
            return
        }

        _state.value = _state.value.copy(
            gitHubError = null
        )
    }

    private fun validateBio(bio: String) {
        val trimmedBio = bio.trim()

        if (trimmedBio.isBlank()) {
            _state.value = _state.value.copy(
                bioError = EditProfileState.BioError.FieldEmpty
            )
            return
        }

        _state.value = _state.value.copy(
            bioError = null
        )
    }
}
