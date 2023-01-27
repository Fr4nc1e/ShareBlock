package com.code.block.feature.chat.domain.model

data class Chat(
    val username: String,
    val profileUrl: String,
    val latestMessage: String,
    val latestFormattedTime: String
)
