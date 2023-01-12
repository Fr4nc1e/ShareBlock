package com.code.block.feature.auth.data.remote

import com.code.block.core.data.dto.BasicApiResponse
import com.code.block.feature.auth.data.remote.request.CreateAccountRequest
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApi {

    @POST("/api/user/create")
    suspend fun register(
        @Body request: CreateAccountRequest
    ): BasicApiResponse

    companion object {
        const val BASE_URL = "http://10.0.2.2:8081/"
    }
}
