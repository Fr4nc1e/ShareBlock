package com.code.block.feature.profile.data.source.api

import com.code.block.core.data.source.response.* // ktlint-disable no-wildcard-imports
import com.code.block.feature.profile.data.source.request.FollowUpdateRequest
import com.code.block.feature.profile.data.source.response.ProfileResponse
import com.code.block.feature.profile.data.source.response.UserItemResponse
import okhttp3.MultipartBody
import retrofit2.http.* // ktlint-disable no-wildcard-imports

interface ProfileApi {

    @GET("/api/user/profile")
    suspend fun getUserProfile(
        @Query("userId") userId: String,
    ): BasicApiResponse<ProfileResponse>

    @Multipart
    @PUT("/api/user/update")
    suspend fun updateProfile(
        @Part bannerImage: MultipartBody.Part?,
        @Part profilePicture: MultipartBody.Part?,
        @Part updateProfileData: MultipartBody.Part,
    ): UpdateProfileResponse

    @GET("/api/user/search")
    suspend fun searchUser(
        @Query("query") query: String,
    ): List<UserItemResponse>

    @POST("/api/following/follow")
    suspend fun followUser(
        @Body request: FollowUpdateRequest,
    ): FollowUpdateResponse

    @HTTP(method = "DELETE", path = "/api/following/unfollow", hasBody = true)
    suspend fun unfollowUser(
        @Body request: FollowUpdateRequest,
    ): FollowUpdateResponse

    @GET("api/comment/user/get")
    suspend fun getComments(
        @Query("userId") userId: String,
        @Query("page") page: Int,
        @Query("pageSize") pageSize: Int,
    ): CommentsForUserResponse

    @GET("/api/follow/users/followers")
    suspend fun getFollowers(
        @Query("userId") userId: String,
    ): FollowingUsersResponse

    @GET("/api/follow/users/followings")
    suspend fun getFollowings(
        @Query("userId") userId: String,
    ): FollowedUsersResponse
}
