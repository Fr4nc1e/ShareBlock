package com.code.block.feature.profile.data.source

import com.code.block.core.data.dto.BasicApiResponse
import com.code.block.core.data.dto.FollowUpdateResponse
import com.code.block.core.data.dto.UpdateProfileResponse
import com.code.block.feature.profile.data.source.request.FollowUpdateRequest
import com.code.block.feature.profile.data.source.response.ProfileResponse
import com.code.block.feature.profile.data.source.response.UserItemDto
import okhttp3.MultipartBody
import retrofit2.http.* // ktlint-disable no-wildcard-imports

interface ProfileApi {

    @GET("/api/user/profile")
    suspend fun getUserProfile(
        @Query("userId") userId: String
    ): BasicApiResponse<ProfileResponse>

    @Multipart
    @PUT("/api/user/update")
    suspend fun updateProfile(
        @Part bannerImage: MultipartBody.Part?,
        @Part profilePicture: MultipartBody.Part?,
        @Part updateProfileData: MultipartBody.Part
    ): UpdateProfileResponse

    @GET("/api/user/search")
    suspend fun searchUser(
        @Query("query") query: String
    ): List<UserItemDto>

    @POST("/api/following/follow")
    suspend fun followUser(
        @Body request: FollowUpdateRequest
    ): FollowUpdateResponse

    @DELETE("/api/following/unfollow")
    suspend fun unfollowUser(
        @Body request: FollowUpdateRequest
    ): FollowUpdateResponse

    companion object {
        const val BASE_URL = "http://172.28.211.51:8081/"
    }
}
