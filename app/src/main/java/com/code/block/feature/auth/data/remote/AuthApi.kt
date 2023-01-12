package com.code.block.feature.auth.data.remote

import com.code.block.core.data.dto.BasicApiResponse
import com.code.block.feature.auth.data.remote.request.CreateAccountRequest
import com.code.block.feature.auth.data.remote.request.LoginRequest
import com.code.block.feature.auth.data.remote.response.AuthResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface AuthApi {

    @POST("/api/user/create")
    suspend fun register(
        @Body request: CreateAccountRequest
    ): BasicApiResponse<Unit>

    @POST("/api/user/login")
    suspend fun login(
        @Body request: LoginRequest
    ): BasicApiResponse<AuthResponse>

    @GET("/api/user/authenticate")
    suspend fun authenticate()

    companion object {
        const val BASE_URL = "http://172.28.211.51:8081/"
    }
}
