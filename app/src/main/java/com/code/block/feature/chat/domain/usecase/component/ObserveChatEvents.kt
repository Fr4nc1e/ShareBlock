package com.code.block.feature.chat.domain.usecase.component

import com.code.block.feature.chat.domain.repository.ChatRepository
import com.tinder.scarlet.WebSocket
import kotlinx.coroutines.flow.Flow

class ObserveChatEvents(
    private val repository: ChatRepository
) {
    operator fun invoke(): Flow<WebSocket.Event> {
        return repository.observeChatEvent()
    }
}
