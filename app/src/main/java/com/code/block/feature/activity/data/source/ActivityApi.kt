package com.code.block.feature.activity.data.source

import com.code.block.core.util.Constants
import com.code.block.feature.activity.data.source.response.ActivityDto
import retrofit2.http.GET
import retrofit2.http.Query

interface ActivityApi {

    @GET("/api/activity/get")
    suspend fun getActivities(
        @Query("page") page: Int = 0,
        @Query("pageSize") pageSize: Int = Constants.PAGE_SIZE_POSTS
    ): List<ActivityDto>

    companion object {
        const val BASE_URL = "http://172.28.211.51:8081/"
    }
}
