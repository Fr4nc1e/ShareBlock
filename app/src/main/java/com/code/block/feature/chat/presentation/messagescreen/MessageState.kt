package com.code.block.feature.chat.presentation.messagescreen

import com.code.block.feature.chat.domain.model.Message

data class MessageState(
    val messageList: List<Message> = emptyList(),
    val isLoading: Boolean = false,
    val canSendMessage: Boolean = false,
)
