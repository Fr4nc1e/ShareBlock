package com.code.block.feature.chat.data.repository

import com.code.block.R
import com.code.block.core.domain.resource.ChannelIdResource
import com.code.block.core.domain.resource.Resource
import com.code.block.core.util.ui.UiText
import com.code.block.feature.chat.data.service.ChatService
import com.code.block.feature.chat.data.source.api.ChatApi
import com.code.block.feature.chat.data.source.ws.model.WsClientMessage
import com.code.block.feature.chat.di.ScarletInstance
import com.code.block.feature.chat.domain.model.Chat
import com.code.block.feature.chat.domain.model.Message
import com.code.block.feature.chat.domain.repository.ChatRepository
import com.tinder.scarlet.WebSocket
import java.io.IOException
import kotlinx.coroutines.flow.* // ktlint-disable no-wildcard-imports
import okhttp3.OkHttpClient
import retrofit2.HttpException

class ChatRepositoryImpl(
    private val chatApi: ChatApi,
    private val okHttpClient: OkHttpClient
) : ChatRepository {
    private var chatService: ChatService? = null

    override fun initialize() {
        chatService = ScarletInstance.getNewInstance(okHttpClient)
    }

    override suspend fun getChannelId(userId: String): ChannelIdResource {
        return try {
            Resource.Success(
                data = chatApi.getChannelId(userId).data,
                uiText = null
            )
        } catch (e: IOException) {
            Resource.Error(
                uiText = UiText.StringResource(R.string.fail_to_connect)
            )
        } catch (e: HttpException) {
            Resource.Error(
                uiText = UiText.StringResource(R.string.fail_to_connect)
            )
        }
    }

    override suspend fun getChatsForUser(): Resource<List<Chat>> {
        return try {
            val chats = chatApi
                .getChatsForUser()
                .mapNotNull { it.toChat() }
            println(chats)
            Resource.Success(data = chats, uiText = null)
        } catch (e: IOException) {
            Resource.Error(
                uiText = UiText.StringResource(R.string.fail_to_connect)
            )
        } catch (e: HttpException) {
            Resource.Error(
                uiText = UiText.StringResource(R.string.fail_to_connect)
            )
        }
    }

    override suspend fun getMessagesForChat(
        chatId: String,
        page: Int,
        pageSize: Int
    ): Resource<List<Message>> {
        return try {
            val messages = chatApi
                .getMessagesForChat(chatId, page, pageSize)
                .map { it.toMessage() }
            Resource.Success(data = messages, uiText = null)
        } catch (e: IOException) {
            Resource.Error(
                uiText = UiText.StringResource(R.string.fail_to_connect)
            )
        } catch (e: HttpException) {
            Resource.Error(
                uiText = UiText.StringResource(R.string.fail_to_connect)
            )
        }
    }

    override fun observeChatEvent(): Flow<WebSocket.Event> {
        return chatService?.observeEvents()
            ?.receiveAsFlow() ?: flow { }
    }

    override fun observeMessage(): Flow<Message> {
        return chatService
            ?.observeMessages()
            ?.receiveAsFlow()
            ?.map { it.toMessage() } ?: flow { }
    }

    override fun sendMessage(toId: String, text: String, chatId: String?) {
        chatService?.sendMessage(
            WsClientMessage(
                toId = toId,
                text = text,
                chatId = chatId
            )
        )
    }
}
