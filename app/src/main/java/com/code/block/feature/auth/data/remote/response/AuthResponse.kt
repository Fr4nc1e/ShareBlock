package com.code.block.feature.auth.data.remote.response

import kotlinx.serialization.Serializable

@Serializable
data class AuthResponse(
    val token: String
)
