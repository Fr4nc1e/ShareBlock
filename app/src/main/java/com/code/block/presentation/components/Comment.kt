package com.code.block.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.* // ktlint-disable no-wildcard-imports
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.* // ktlint-disable no-wildcard-imports
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.code.block.domain.model.Comment
import com.code.block.ui.theme.ProfilePictureSizeExtraSmall
import com.code.block.ui.theme.SpaceMedium
import com.code.block.ui.theme.SpaceSmall
import com.code.block.ui.theme.TextWhite

@Composable
fun Comment(
    modifier: Modifier = Modifier,
    comment: Comment,
    onLikeClick: (Boolean) -> Unit = {}
) {
    Card(
        modifier = modifier,
        elevation = 5.dp,
        shape = MaterialTheme.shapes.medium,
        backgroundColor = MaterialTheme.colors.surface
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(SpaceMedium)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(
                    verticalAlignment = CenterVertically
                ) {
                    Image(
                        painter = painterResource(id = comment.profilePictureUrl),
                        contentDescription = null,
                        modifier = Modifier
                            .clip(CircleShape)
                            .size(ProfilePictureSizeExtraSmall)
                    )

                    Spacer(modifier = Modifier.width(SpaceSmall))

                    Text(
                        text = comment.username,
                        fontWeight = FontWeight.Bold,
                        style = MaterialTheme.typography.body2,
                        color = MaterialTheme.colors.onBackground
                    )
                }

                Text(
                    text = "2 days ago",
                    style = MaterialTheme.typography.body2,
                    modifier = Modifier.align(CenterVertically)
                )
            }

            Text(
                text = comment.comment,
                style = MaterialTheme.typography.body2,
                color = MaterialTheme.colors.onBackground
            )

            Spacer(modifier = Modifier.height(SpaceSmall))

            Row(
                verticalAlignment = CenterVertically
            ) {
                IconButton(
                    onClick = {
                        onLikeClick(comment.isLiked)
                    },
                    modifier = Modifier.size(20.dp)
                ) {
                    Icon(
                        imageVector = Icons.Filled.Favorite,
                        contentDescription = if (comment.isLiked) {
                            stringResource(com.code.block.R.string.unlike)
                        } else {
                            stringResource(com.code.block.R.string.like)
                        },
                        tint = if (comment.isLiked) {
                            Color.Red
                        } else {
                            TextWhite
                        }
                    )
                }

                Spacer(modifier = Modifier.width(SpaceSmall))

                Text(
                    text = "${comment.likeCount}",
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    style = MaterialTheme.typography.h2
                )
            }
        }
    }
}
