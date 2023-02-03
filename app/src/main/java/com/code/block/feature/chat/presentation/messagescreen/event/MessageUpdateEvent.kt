package com.code.block.feature.chat.presentation.messagescreen.event

sealed class MessageUpdateEvent {
    object SingleMessageUpdate : MessageUpdateEvent()
    object MessagePageLoaded : MessageUpdateEvent()
    object MessageSent : MessageUpdateEvent()
}
