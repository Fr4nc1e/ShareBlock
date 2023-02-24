package com.code.block.usecase.chat.component

import com.code.block.core.domain.resource.ChannelIdResource
import com.code.block.feature.chat.domain.repository.ChatRepository

class GetChannelIdUseCase(
    private val repository: ChatRepository
) {
    suspend operator fun invoke(userId: String): ChannelIdResource {
        return repository.getChannelId(userId)
    }
}
