package com.code.block.feature.chat.data.source.ws.model

data class WsClientMessage(
    val toId: String,
    val text: String,
    val chatId: String?,
)
