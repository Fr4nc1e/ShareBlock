package com.code.block.feature.profile.domain.model

data class UpdateProfileData(
    val username: String,
    val bio: String,
    val gitHubUrl: String,
    val weChatUrl: String,
    val qqUrl: String,
)
