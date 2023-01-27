package com.code.block.feature.chat.presentation.messagescreen.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.* // ktlint-disable no-wildcard-imports
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.code.block.feature.chat.domain.model.Message

@Composable
fun MessageItem(
    message: Message,
    modifier: Modifier = Modifier,
    isOwnMessage: Boolean = true
) {
    Column(
        modifier = modifier
            .width(200.dp)
            .drawBehind {
                val cornerRadius = 10.dp.toPx()
                val triangleHeight = 20.dp.toPx()
                val triangleWidth = 25.dp.toPx()
                val trianglePath = Path().apply {
                    if (isOwnMessage) {
                        moveTo(size.width, size.height - cornerRadius)
                        lineTo(size.width, size.height + triangleHeight)
                        lineTo(size.width - triangleWidth, size.height - cornerRadius)
                        close()
                    } else {
                        moveTo(0f, size.height - cornerRadius)
                        lineTo(0f, size.height + triangleHeight)
                        lineTo(triangleWidth, size.height - cornerRadius)
                        close()
                    }
                }
                drawPath(
                    path = trianglePath,
                    color = if (isOwnMessage) Color.Green else Color.DarkGray
                )
            }
            .background(
                color = if (isOwnMessage) Color.Green else Color.DarkGray,
                shape = RoundedCornerShape(10.dp)
            )
            .padding(8.dp)
    ) {
        Text(
            text = message.formUserId,
            fontWeight = FontWeight.Bold,
            color = Color.White
        )
        Text(
            text = message.messageText,
            color = Color.White
        )
        Text(
            text = message.formattedTime,
            color = Color.White,
            modifier = Modifier.align(Alignment.End)
        )
    }
}
