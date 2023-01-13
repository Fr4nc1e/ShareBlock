package com.code.block.feature.auth.data.remote

import com.code.block.core.data.dto.LoginResponse
import com.code.block.core.data.dto.RegisterResponse
import com.code.block.feature.auth.data.remote.request.CreateAccountRequest
import com.code.block.feature.auth.data.remote.request.LoginRequest
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface AuthApi {

    @POST("/api/user/create")
    suspend fun register(
        @Body request: CreateAccountRequest
    ): RegisterResponse

    @POST("/api/user/login")
    suspend fun login(
        @Body request: LoginRequest
    ): LoginResponse

    @GET("/api/user/authenticate")
    suspend fun authenticate()

    companion object {
        const val BASE_URL = "http://10.0.2.2:8081/"
    }
}
