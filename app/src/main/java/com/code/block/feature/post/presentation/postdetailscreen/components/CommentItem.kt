package com.code.block.feature.post.presentation.postdetailscreen.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.* // ktlint-disable no-wildcard-imports
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.* // ktlint-disable no-wildcard-imports
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.code.block.core.domain.model.Comment
import com.code.block.core.presentation.components.Screen
import com.code.block.core.presentation.ui.theme.IconSizeSmall
import com.code.block.core.presentation.ui.theme.ProfilePictureSizeSmall
import com.code.block.core.presentation.ui.theme.SpaceSmall
import com.code.block.core.presentation.ui.theme.quicksand

@Composable
fun CommentItem(
    modifier: Modifier = Modifier,
    comment: Comment,
    onLikeClick: (Boolean) -> Unit = {},
    onUserClick: (String) -> Unit = {},
    onItemClick: () -> Unit = {}
) {
    Card(
        modifier = modifier.clickable { onItemClick() },
        elevation = 5.dp,
        shape = RoundedCornerShape(10.dp),
        backgroundColor = MaterialTheme.colors.surface
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(SpaceSmall)
        ) {
            Box(
                modifier = Modifier
                    .align(Alignment.Top)
                    .clickable {
                        onUserClick(Screen.ProfileScreen.route + "?userId=${comment.userId}")
                    }
            ) {
                Image(
                    painter = rememberAsyncImagePainter(
                        ImageRequest.Builder(LocalContext.current)
                            .data(data = comment.profilePictureUrl)
                            .apply(
                                block = fun ImageRequest.Builder.() { crossfade(true) }
                            ).build()
                    ),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
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
                                    Color(0xFF9575CD)
                                )
                            ),
                            shape = CircleShape
                        )
                        .align(Center)
                )
            }

            Spacer(modifier = Modifier.width(SpaceSmall))

            Column {
                Text(
                    text = buildAnnotatedString {
                        withStyle(
                            style = SpanStyle(
                                fontFamily = quicksand,
                                fontWeight = FontWeight.Bold,
                                fontSize = 16.sp,
                                color = MaterialTheme.colors.primary
                            )
                        ) {
                            append(comment.username)
                        }
                        append("    " + comment.comment)
                    },
                    style = MaterialTheme.typography.body1,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colors.onSurface
                )

                Spacer(modifier = Modifier.height(SpaceSmall))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = CenterVertically
                ) {
                    Row(
                        verticalAlignment = CenterVertically
                    ) {
                        IconButton(
                            onClick = {
                                onLikeClick(comment.isLiked)
                            },
                            modifier = Modifier.size(IconSizeSmall)
                        ) {
                            Icon(
                                imageVector = Icons.Filled.Favorite,
                                contentDescription = if (comment.isLiked) {
                                    stringResource(com.code.block.R.string.unlike)
                                } else {
                                    stringResource(com.code.block.R.string.like)
                                },
                                tint = if (comment.isLiked) {
                                    MaterialTheme.colors.primary
                                } else {
                                    MaterialTheme.colors.onSurface
                                }
                            )
                        }

                        Spacer(modifier = Modifier.width(SpaceSmall))

                        Text(
                            text = "${comment.likeCount}",
                            fontWeight = FontWeight.Bold,
                            style = MaterialTheme.typography.body2,
                            color = MaterialTheme.colors.onSurface
                        )
                    }

                    Text(
                        text = comment.formattedTime,
                        style = MaterialTheme.typography.body2,
                        color = MaterialTheme.colors.onSurface
                    )
                }
            }
        }
    }
}
