package com.code.block.feature.chat.presentation.messagescreen

sealed class MessageEvent {
    object SendMessage : MessageEvent()
    data class EnteredMessage(val messageText: String) : MessageEvent()
}
