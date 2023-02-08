package com.code.block.feature.profile.domain.state

import com.code.block.feature.profile.domain.error.EditProfileError

data class EditTextState(
    val text: String = "",
    val error: EditProfileError? = null,
)
