package com.code.block.feature.chat.presentation.chatscreen

import com.code.block.feature.chat.domain.model.Chat

data class ChatState(
    val chats: List<Chat> = emptyList(),
    val isLoading: Boolean = false,
)
