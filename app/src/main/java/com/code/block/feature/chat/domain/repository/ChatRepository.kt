package com.code.block.feature.chat.domain.repository

import com.code.block.core.domain.resource.Resource
import com.code.block.feature.chat.domain.model.Chat
import com.code.block.feature.chat.domain.model.Message
import com.tinder.scarlet.WebSocket
import kotlinx.coroutines.flow.Flow

interface ChatRepository {
    suspend fun getChatsForUser(): Resource<List<Chat>>

    suspend fun getMessagesForChat(
        chatId: String,
        page: Int,
        pageSize: Int
    ): Resource<List<Message>>

    fun observeChatEvent(): Flow<WebSocket.Event>

    fun observeMessage(): Flow<Message>

    fun sendMessage(
        toId: String,
        text: String,
        chatId: String?
    )

    fun initialize()
}
