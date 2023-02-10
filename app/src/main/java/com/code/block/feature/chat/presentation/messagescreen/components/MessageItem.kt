package com.code.block.feature.chat.presentation.messagescreen.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.* // ktlint-disable no-wildcard-imports
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.code.block.core.presentation.ui.theme.ProfilePictureSizeSmall
import com.code.block.core.presentation.ui.theme.SpaceSmall
import com.code.block.core.util.ui.ImageLoader
import com.code.block.feature.chat.domain.model.Message

@Composable
fun MessageItem(
    modifier: Modifier = Modifier,
    message: Message,
    remoteProfileUrl: String?,
    ownProfilePictureUrl: String?,
    ownColor: List<Color?> = listOf(),
    remoteColor: List<Color?> = listOf(),
    isOwnMessage: Boolean = true,
    onRemoteUserClick: () -> Unit = {},
    onOwnUserClick: () -> Unit = {},
) {
    Box(modifier = Modifier.wrapContentSize()) {
        Row(
            modifier = Modifier.wrapContentSize(),
        ) {
            if (!isOwnMessage) {
                ImageLoader(
                    url = remoteProfileUrl,
                    modifier = Modifier
                        .size(ProfilePictureSizeSmall)
                        .clip(CircleShape)
                        .border(
                            width = 1.dp,
                            brush = Brush.sweepGradient(
                                listOf(
                                    Color(0xFF9575CD),
                                    Color(0xFFBA68C8),
                                    Color(0xFFE57373),
                                    Color(0xFFFFB74D),
                                    Color(0xFFFFF176),
                                    Color(0xFFAED581),
                                    Color(0xFF4DD0E1),
                                    Color(0xFF9575CD),
                                ),
                            ),
                            shape = CircleShape,
                        )
                        .clickable { onRemoteUserClick() },
                )
                Spacer(modifier = Modifier.width(SpaceSmall))
            }

            Column(
                modifier = modifier
                    .width(200.dp)
                    .background(
                        color = if (isOwnMessage) {
                            ownColor.first() ?: MaterialTheme.colors.primary
                        } else { remoteColor.first() ?: Color.DarkGray },
                        shape = RoundedCornerShape(10.dp),
                    )
                    .padding(8.dp),
            ) {
                Text(
                    text = message.text,
                    style = MaterialTheme.typography.body1.copy(
                        fontSize = 14.sp,
                    ),
                    color = if (isOwnMessage) {
                        ownColor.last() ?: MaterialTheme.colors.primary
                    } else { remoteColor.last() ?: Color.White },
                )
                Text(
                    text = message.formattedTime,
                    style = MaterialTheme.typography.body2,
                    color = if (isOwnMessage) {
                        ownColor.last() ?: MaterialTheme.colors.primary
                    } else { remoteColor.last() ?: Color.White },
                    modifier = Modifier.align(Alignment.End),
                )
            }

            if (isOwnMessage) {
                Spacer(modifier = Modifier.width(SpaceSmall))
                ImageLoader(
                    url = ownProfilePictureUrl,
                    modifier = Modifier
                        .size(ProfilePictureSizeSmall)
                        .clip(CircleShape)
                        .border(
                            width = 1.dp,
                            brush = Brush.sweepGradient(
                                listOf(
                                    Color(0xFF9575CD),
                                    Color(0xFFBA68C8),
                                    Color(0xFFE57373),
                                    Color(0xFFFFB74D),
                                    Color(0xFFFFF176),
                                    Color(0xFFAED581),
                                    Color(0xFF4DD0E1),
                                    Color(0xFF9575CD),
                                ),
                            ),
                            shape = CircleShape,
                        ).clickable { onOwnUserClick() },
                )
            }
        }
    }
}
