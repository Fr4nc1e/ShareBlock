package com.code.block.feature.chat.data.source.ws.model

import com.code.block.feature.chat.domain.model.Message
import java.text.DateFormat
import java.util.*

data class WsMessage(
    val fromId: String,
    val toId: String,
    val text: String,
    val timestamp: Long,
    val chatId: String?
) {
    fun toMessage(): Message {
        return Message(
            fromId = fromId,
            toId = toId,
            text = text,
            formattedTime = DateFormat
                .getDateInstance(DateFormat.DEFAULT)
                .format(Date(timestamp)),
            chatId = chatId
        )
    }
}
