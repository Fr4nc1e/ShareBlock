package com.code.block.usecase.chat

import com.code.block.usecase.chat.component.* // ktlint-disable no-wildcard-imports

data class ChatUseCases(
    val getMessagesForChatUseCase: GetMessagesForChatUseCase,
    val getChatsForUserUseCase: GetChatsForUserUseCase,
    val observeChatEvents: ObserveChatEvents,
    val observeMessages: ObserveMessages,
    val sendMessage: SendMessage,
    val initRepositoryUseCase: InitRepositoryUseCase,
    val getChannelIdUseCase: GetChannelIdUseCase
)
