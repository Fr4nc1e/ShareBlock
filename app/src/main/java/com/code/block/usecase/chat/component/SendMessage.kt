package com.code.block.usecase.chat.component

import com.code.block.feature.chat.domain.repository.ChatRepository

class SendMessage(
    private val repository: ChatRepository
) {
    operator fun invoke(
        toId: String,
        text: String,
        chatId: String?
    ) {
        if (text.isBlank()) return
        repository.sendMessage(
            toId = toId,
            text = text.trim(),
            chatId = chatId
        )
    }
}
