package com.code.block.feature.chat.domain.usecase

import com.code.block.feature.chat.domain.usecase.component.* // ktlint-disable no-wildcard-imports

data class ChatUseCases(
    val getMessagesForChatUseCase: GetMessagesForChatUseCase,
    val getChatsForUserUseCase: GetChatsForUserUseCase,
    val observeChatEvents: ObserveChatEvents,
    val observeMessages: ObserveMessages,
    val sendMessage: SendMessage,
    val initRepositoryUseCase: InitRepositoryUseCase,
)
