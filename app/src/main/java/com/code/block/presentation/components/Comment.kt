package com.code.block.presentation.components

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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.code.block.domain.model.Comment
import com.code.block.ui.theme.* // ktlint-disable no-wildcard-imports

@Composable
fun Comment(
    modifier: Modifier = Modifier,
    comment: Comment,
    onLikeClick: (Boolean) -> Unit = {}
) {
    Card(
        modifier = modifier,
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
                    .clickable { }
            ) {
                Image(
                    painter = painterResource(id = comment.profilePictureUrl),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(ProfilePictureSizeSmall)
                        .clip(CircleShape)
                        .border(
                            width = 1.dp,
                            brush = Brush.horizontalGradient(
                                listOf(
                                    Color.Green,
                                    Color.Blue,
                                    Color.Transparent
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
                                color = MaterialTheme.colors.primary
                            )
                        ) {
                            append(comment.username)
                        }
                        append("    " + comment.comment)
                    },
                    style = MaterialTheme.typography.body2,
                    color = MaterialTheme.colors.onBackground
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

                    Text(
                        text = comment.formattedTime,
                        style = MaterialTheme.typography.body2
                    )
                }
            }
        }
    }
}
