package com.code.block.feature.post.data.source

import com.code.block.core.data.source.response.* // ktlint-disable no-wildcard-imports
import com.code.block.feature.post.data.source.request.CreateCommentRequest
import com.code.block.feature.post.data.source.request.LikeUpdateRequest
import com.code.block.feature.post.data.source.response.PostDto
import okhttp3.MultipartBody
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Query

interface PostApi {

    @GET("/api/post/follow/get")
    suspend fun getHomePosts(
        @Query("page") page: Int,
        @Query("pageSize") pageSize: Int
    ): List<PostDto>

    @GET("/api/user/post")
    suspend fun getPostsForProfile(
        @Query("userId") userId: String,
        @Query("page") page: Int,
        @Query("pageSize") pageSize: Int
    ): List<PostDto>

    @GET("/api/post/like/get")
    suspend fun getPostsForLike(
        @Query("userId") userId: String,
        @Query("page") page: Int,
        @Query("pageSize") pageSize: Int
    ): List<PostDto>

    @Multipart
    @POST("/api/post/create")
    suspend fun createPost(
        @Part postData: MultipartBody.Part,
        @Part postContent: MultipartBody.Part
    ): CreatePostResponse

    @GET("/api/post/details")
    suspend fun getPostDetails(
        @Query("postId") postId: String
    ): PostDetailResponse

    @GET("/api/comment/post/get")
    suspend fun getCommentsForPost(
        @Query("postId") postId: String
    ): CommentsForPostResponse

    @POST("/api/comment/create")
    suspend fun createComment(
        @Body createRequest: CreateCommentRequest
    ): CreateCommentResponse

    @POST("api/like")
    suspend fun likeParent(
        @Body likeUpdateRequest: LikeUpdateRequest
    ): LikeUpdateResponse

    @DELETE("api/unlike")
    suspend fun unlikeParent(
        @Query("parentId") parentId: String,
        @Query("parentType") parentType: Int
    ): LikeUpdateResponse

    @GET("/api/like/parent")
    suspend fun getLikedUsers(
        @Query("parentId") parentId: String
    ): LikedUsersResponse
}
