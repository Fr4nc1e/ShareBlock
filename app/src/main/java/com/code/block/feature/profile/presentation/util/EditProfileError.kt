package com.code.block.feature.profile.presentation.util

sealed class EditProfileError : Error() {
    object FieldEmpty : EditProfileError()
}
