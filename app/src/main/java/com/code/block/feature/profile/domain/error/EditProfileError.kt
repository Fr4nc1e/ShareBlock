package com.code.block.feature.profile.domain.error

sealed class EditProfileError : Error() {
    object FieldEmpty : EditProfileError()
}
