package com.code.block.feature.chat.presentation.messagescreen.event

sealed class MessageEvent {
    object SendMessage : MessageEvent()
    data class EnteredMessage(val messageText: String) : MessageEvent()
}
