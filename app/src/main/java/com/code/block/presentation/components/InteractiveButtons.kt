package com.code.block.presentation.components

import androidx.compose.foundation.layout.* // ktlint-disable no-wildcard-imports
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Comment
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Share
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.code.block.R
import com.code.block.ui.theme.SpaceMedium
import com.code.block.ui.theme.TextWhite

@Composable
fun InteractiveButtons(
    modifier: Modifier = Modifier,
    isLiked: Boolean = false,
    iconSize: Dp = 30.dp,
    onLikeClick: (Boolean) -> Unit = {},
    onCommentClick: () -> Unit = {},
    onShareClick: () -> Unit = {}
) {
    Row(
        horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
    ) {
        IconButton(
            onClick = {
                onLikeClick(!isLiked)
            },
            modifier = Modifier.size(iconSize)
        ) {
            Icon(
                imageVector = Icons.Filled.Favorite,
                contentDescription = if (isLiked) {
                    stringResource(R.string.unlike)
                } else {
                    stringResource(R.string.like)
                },
                tint = if (isLiked) {
                    Color.Red
                } else {
                    TextWhite
                }
            )
        }

        Spacer(modifier = Modifier.height(SpaceMedium))

        IconButton(
            onClick = {
                onCommentClick()
            },
            modifier = Modifier.size(iconSize)
        ) {
            Icon(
                imageVector = Icons.Filled.Comment,
                contentDescription = stringResource(R.string.comment)
            )
        }

        Spacer(modifier = Modifier.height(SpaceMedium))

        IconButton(
            onClick = {
                onShareClick()
            },
            modifier = Modifier.size(iconSize)
        ) {
            Icon(
                imageVector = Icons.Filled.Share,
                contentDescription = stringResource(R.string.share)
            )
        }
    }
}
