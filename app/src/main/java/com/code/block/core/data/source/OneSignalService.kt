package com.code.block.core.data.source

import com.code.block.core.data.source.response.SendPostNotificationResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface OneSignalService {

    @GET("/api/post/notification")
    suspend fun sendPostNotification(
        @Query("title") title: String,
        @Query("description") description: String,
    ): SendPostNotificationResponse
}
