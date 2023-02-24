package com.code.block.feature.auth.data.source.remote.request

data class CreateAccountRequest(
    val email: String,
    val username: String,
    val password: String
)
