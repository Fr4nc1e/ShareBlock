package com.code.block.core.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.* // ktlint-disable no-wildcard-imports
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Comment
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Share
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.code.block.R
import com.code.block.core.domain.model.Post
import com.code.block.core.presentation.ui.theme.IconSizeMedium
import com.code.block.core.presentation.ui.theme.SpaceSmall
import com.code.block.core.presentation.ui.theme.TextWhite

@Composable
fun InteractiveButtons(
    modifier: Modifier = Modifier,
    onNavigate: (String) -> Unit = {},
    post: Post,
    onLikeClick: () -> Unit = {},
    onCommentClick: () -> Unit = {},
    onShareClick: () -> Unit = {}
) {
    Row(
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier.fillMaxWidth()
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(
                onClick = {
                    onLikeClick()
                },
                modifier = Modifier.size(IconSizeMedium)
            ) {
                Icon(
                    imageVector = Icons.Filled.Favorite,
                    contentDescription = if (post.isLiked) {
                        stringResource(R.string.unlike)
                    } else {
                        stringResource(R.string.like)
                    },
                    tint = if (post.isLiked) {
                        MaterialTheme.colors.primary
                    } else {
                        TextWhite
                    }
                )
            }

            Spacer(modifier = Modifier.width(SpaceSmall))

            Text(
                text = "${post.likeCount}",
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
                style = MaterialTheme.typography.h2,
                modifier = Modifier.clickable {
                    onNavigate(Screen.PersonListScreen.route + "/${post.id}")
                }
            )
        }

        Row(
            verticalAlignment = Alignment.CenterVertically
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
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
                style = MaterialTheme.typography.h2
            )
        }

        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(
                onClick = {
                    onShareClick()
                },
                modifier = Modifier.size(IconSizeMedium)
            ) {
                Icon(
                    imageVector = Icons.Filled.Share,
                    contentDescription = stringResource(R.string.share)
                )
            }
        }
    }
}
