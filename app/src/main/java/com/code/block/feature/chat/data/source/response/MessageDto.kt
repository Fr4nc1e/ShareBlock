package com.code.block.feature.chat.data.source.response

import com.code.block.feature.chat.domain.model.Message
import java.text.SimpleDateFormat
import java.util.*

data class MessageDto(
    val fromId: String,
    val toId: String,
    val text: String,
    val timestamp: Long,
    val chatId: String?,
    val id: String
) {
    fun toMessage(): Message {
        return Message(
            fromId = fromId,
            toId = toId,
            text = text,
            formattedTime = SimpleDateFormat(
                "MMM dd, HH:mm",
                Locale.getDefault()
            ).format(timestamp),
            chatId = chatId
        )
    }
}
