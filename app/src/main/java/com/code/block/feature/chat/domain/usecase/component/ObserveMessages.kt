package com.code.block.feature.chat.domain.usecase.component

import com.code.block.feature.chat.domain.model.Message
import com.code.block.feature.chat.domain.repository.ChatRepository
import kotlinx.coroutines.flow.Flow

class ObserveMessages(
    private val repository: ChatRepository
) {
    operator fun invoke(): Flow<Message> {
        return repository.observeMessage()
    }
}
