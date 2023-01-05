package com.code.block.domain.model

import com.code.block.domain.util.ActivityType

data class Activity(
    val username: String,
    val profileImageUrl: Int,
    val activityType: ActivityType,
    val formattedTime: String
)
