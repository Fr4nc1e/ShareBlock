package com.code.block.feature.profile.data.source

import com.code.block.core.data.dto.BasicApiResponse
import com.code.block.feature.profile.data.source.response.ProfileResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ProfileApi {

    @GET("/api/user/profile")
    suspend fun getUserProfile(
        @Query("userId") userId: String
    ): BasicApiResponse<ProfileResponse>

    companion object {
        const val BASE_URL = "http://172.28.211.51:8081/"
    }
}
