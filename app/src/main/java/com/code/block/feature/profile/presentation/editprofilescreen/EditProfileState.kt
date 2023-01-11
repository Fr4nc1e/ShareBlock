package com.code.block.feature.profile.presentation.editprofilescreen

data class EditProfileState(
    val usernameText: String = "",
    val usernameError: UsernameError? = null,
    val qqText: String = "",
    val qqError: QqError? = null,
    val weChatText: String = "",
    val weChatError: WeChatError? = null,
    val gitHubText: String = "",
    val gitHubError: GitHubError? = null,
    val bioText: String = "",
    val bioError: BioError? = null
) {
    sealed class UsernameError {
        object FieldEmpty : UsernameError()
        object InputTooShort : UsernameError()
    }

    sealed class QqError {
        object FieldEmpty : QqError()
    }

    sealed class WeChatError {
        object FieldEmpty : WeChatError()
    }

    sealed class GitHubError {
        object FieldEmpty : GitHubError()
    }

    sealed class BioError {
        object FieldEmpty : BioError()
    }
}
