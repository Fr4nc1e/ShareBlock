package com.code.block.core.domain.state

import com.code.block.core.domain.util.Error

data class TextFieldState(
    val text: String = "",
    val error: Error? = null
)
