package com.code.block.feature.chat.domain.model

data class Message(
    val formUserId: String,
    val toUserId: String,
    val messageText: String,
    val formattedTime: String,
    val parentId: String,
    val id: String
)
