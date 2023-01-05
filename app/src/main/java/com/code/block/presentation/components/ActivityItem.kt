package com.code.block.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.* // ktlint-disable no-wildcard-imports
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
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
import com.code.block.R
import com.code.block.domain.model.Activity
import com.code.block.domain.util.ActivityType
import com.code.block.ui.theme.ProfilePictureSizeExtraSmall
import com.code.block.ui.theme.SpaceSmall
import com.code.block.ui.theme.quicksand

@Composable
fun ActivityItem(
    activity: Activity,
    modifier: Modifier = Modifier
) {
    Card(
        shape = MaterialTheme.shapes.medium,
        backgroundColor = MaterialTheme.colors.surface,
        elevation = 5.dp
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(SpaceSmall),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row {
                Image(
                    painter = painterResource(id = activity.profileImageUrl),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(ProfilePictureSizeExtraSmall)
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
                        .align(Alignment.CenterVertically)
                )

                Spacer(modifier = Modifier.width(SpaceSmall))

                Column {
                    val fillerText = when (activity.activityType) {
                        is ActivityType.LikedPost, ActivityType.LikedComment -> stringResource(R.string.liked)
                        is ActivityType.CommentedOnPost -> stringResource(R.string.commented_on)
                        is ActivityType.FollowedYou -> stringResource(R.string.followed_you)
                    }

                    val actionText = when (activity.activityType) {
                        is ActivityType.LikedPost -> stringResource(id = R.string.your_post)
                        is ActivityType.LikedComment -> stringResource(R.string.your_comment)
                        is ActivityType.CommentedOnPost -> stringResource(R.string.your_post)
                        is ActivityType.FollowedYou -> ""
                    }

                    Text(
                        text = activity.username,
                        fontFamily = quicksand,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colors.primary
                    )

                    Spacer(modifier = Modifier.height(SpaceSmall))

                    Text(
                        text = buildAnnotatedString {
                            append(" $fillerText ")
                            withStyle(
                                SpanStyle(
                                    fontWeight = FontWeight.Bold
                                )
                            ) {
                                append(actionText)
                            }
                        },
                        fontSize = 12.sp,
                        color = MaterialTheme.colors.onBackground
                    )
                }
            }

            Text(
                text = activity.formattedTime,
                fontSize = 12.sp,
                color = MaterialTheme.colors.onBackground
            )
        }
    }
}
