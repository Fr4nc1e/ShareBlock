package com.code.block.feature.auth.data.source.api

import com.code.block.core.data.source.response.LoginResponse
import com.code.block.core.data.source.response.RegisterResponse
import com.code.block.feature.auth.data.source.remote.request.CreateAccountRequest
import com.code.block.feature.auth.data.source.remote.request.LoginRequest
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface AuthApi {

    @POST("/api/user/create")
    suspend fun register(
        @Body request: CreateAccountRequest,
    ): RegisterResponse

    @POST("/api/user/login")
    suspend fun login(
        @Body request: LoginRequest,
    ): LoginResponse

    @GET("/api/user/authenticate")
    suspend fun authenticate()
}
