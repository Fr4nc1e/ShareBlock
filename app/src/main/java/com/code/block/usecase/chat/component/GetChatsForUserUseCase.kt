package com.code.block.usecase.chat.component

import com.code.block.core.domain.resource.Resource
import com.code.block.feature.chat.domain.model.Chat
import com.code.block.feature.chat.domain.repository.ChatRepository

class GetChatsForUserUseCase(
    private val repository: ChatRepository,
) {
    suspend operator fun invoke(): Resource<List<Chat>> {
        return repository.getChatsForUser()
    }
}
