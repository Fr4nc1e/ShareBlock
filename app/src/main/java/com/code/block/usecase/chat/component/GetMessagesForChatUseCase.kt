package com.code.block.usecase.chat.component

import com.code.block.core.domain.resource.Resource
import com.code.block.core.util.Constants
import com.code.block.feature.chat.domain.model.Message
import com.code.block.feature.chat.domain.repository.ChatRepository

class GetMessagesForChatUseCase(
    private val repository: ChatRepository
) {
    suspend operator fun invoke(
        chatId: String,
        page: Int,
        pageSize: Int = Constants.PAGE_SIZE_POSTS
    ): Resource<List<Message>> {
        return repository.getMessagesForChat(
            chatId = chatId,
            page = page,
            pageSize = pageSize
        )
    }
}
