package com.code.block.feature.post.data.source.remote

import com.code.block.core.domain.model.Post
import retrofit2.http.GET
import retrofit2.http.Query

interface PostApi {

    @GET("/api/post/follow/get")
    suspend fun getPostForFollows(
        @Query("page") page: Int,
        @Query("pageSize") pageSize: Int
    ): List<Post>

    companion object {
        const val BASE_URL = "http://10.0.2.2:8081/"
    }
}
