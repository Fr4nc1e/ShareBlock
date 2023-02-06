package com.code.block.core.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.* // ktlint-disable no-wildcard-imports
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Comment
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Share
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.code.block.R
import com.code.block.core.domain.model.Post
import com.code.block.core.presentation.ui.theme.IconSizeMedium
import com.code.block.core.presentation.ui.theme.SpaceSmall
import com.popovanton0.heartswitch.HeartSwitch
import com.popovanton0.heartswitch.HeartSwitchColors

@Composable
fun InteractiveButtons(
    modifier: Modifier = Modifier,
    onNavigate: (String) -> Unit = {},
    post: Post,
    isLiked: Boolean,
    showDeleteIcon: Boolean = true,
    onLikeClick: () -> Unit = {},
    onCommentClick: () -> Unit = {},
    onShareClick: () -> Unit = {},
    onDeleteClick: () -> Unit = {}
) {
    Row(
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = CenterVertically,
        modifier = modifier.fillMaxWidth()
    ) {
        Row(
            verticalAlignment = CenterVertically
        ) {
            HeartSwitch(
                checked = isLiked,
                onCheckedChange = {
                    onLikeClick()
                },
                modifier = Modifier.size(IconSizeMedium),
                colors = HeartSwitchColors(
                    checkedTrackColor = Color(0xFFE91E63),
                    checkedTrackBorderColor = Color(0xFFC2185B),
                    uncheckedTrackBorderColor = Color(0xFFBDBDBD)
                ),
                thumb = { modifier, color ->
                    Box(
                        modifier = modifier
                            .shadow(12.dp, CircleShape)
                            .background(color.value, CircleShape)
                    )
                },
                enabled = true,
                interactionSource = remember { MutableInteractionSource() }
            )

            Spacer(modifier = Modifier.width(SpaceSmall))

            Text(
                text = "${post.likeCount}",
                style = MaterialTheme.typography.h2,
                color = MaterialTheme.colors.onSurface,
                modifier = Modifier.clickable {
                    onNavigate(Screen.PersonListScreen.route + "/${post.id}")
                }
            )
        }

        Row(
            verticalAlignment = CenterVertically
        ) {
            IconButton(
                onClick = {
                    onCommentClick()
                },
                modifier = Modifier.size(IconSizeMedium)
            ) {
                Icon(
                    imageVector = Icons.Filled.Comment,
                    contentDescription = stringResource(R.string.comment)
                )
            }

            Spacer(modifier = Modifier.width(SpaceSmall))

            Text(
                text = "${post.commentCount}",
                style = MaterialTheme.typography.h2,
                color = MaterialTheme.colors.onSurface
            )
        }

        IconButton(
            onClick = {
                onShareClick()
            },
            modifier = Modifier.size(IconSizeMedium)
                .align(CenterVertically)
        ) {
            Icon(
                imageVector = Icons.Filled.Share,
                contentDescription = stringResource(R.string.share)
            )
        }

        if (post.isOwnPost && showDeleteIcon) {
            IconButton(
                onClick = {
                    onDeleteClick()
                },
                modifier = Modifier.size(IconSizeMedium)
                    .align(CenterVertically)
            ) {
                Icon(
                    imageVector = Icons.Filled.Delete,
                    contentDescription = stringResource(R.string.delete)
                )
            }
        }
    }
}
