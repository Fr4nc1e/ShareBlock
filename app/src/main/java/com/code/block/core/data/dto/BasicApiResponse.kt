package com.code.block.core.data.dto

import com.code.block.feature.auth.data.source.remote.response.AuthResponse
import com.code.block.feature.profile.data.source.response.ProfileResponse

data class BasicApiResponse<T>(
    val successful: Boolean,
    val message: String? = null,
    val data: T? = null
)

typealias RegisterResponse = BasicApiResponse<Unit>
typealias LoginResponse = BasicApiResponse<AuthResponse>
typealias CreatePostResponse = BasicApiResponse<Unit>
typealias ProfileResponse = BasicApiResponse<ProfileResponse>
