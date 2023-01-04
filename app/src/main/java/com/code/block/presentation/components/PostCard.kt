package com.code.block.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.* // ktlint-disable no-wildcard-imports
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.* // ktlint-disable no-wildcard-imports
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.code.block.R
import com.code.block.domain.model.Post
import com.code.block.ui.theme.* // ktlint-disable no-wildcard-imports

@Composable
fun PostCard(
    post: Post,
    modifier: Modifier = Modifier,
    showProfileImage: Boolean = true,
    onPostClick: () -> Unit = {}
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(SpaceMedium)
    ) {
        Column(
            modifier = modifier
                .fillMaxWidth()
                .offset(
                    y = if (showProfileImage) {
                        ProfilePictureSizeMedium / 2f
                    } else 0.dp
                )
                .clip(MaterialTheme.shapes.medium)
                .shadow(5.dp)
                .background(MediumGray)
                .clickable {
                    onPostClick()
                }
        ) {
            Image(
                painter = painterResource(id = R.drawable.bat_man_front),
                contentDescription = stringResource(
                    id = R.string.front_image_sample
                )
            )

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(SpaceMedium)
            ) {
                ActionRow(
                    username = post.username,
                    modifier = Modifier.fillMaxWidth(),
                    onLikeClick = {},
                    onCommentClick = {},
                    onShareClick = {},
                    onUsernameClick = {}
                )

                Spacer(modifier = Modifier.height(SpaceSmall))

                ExpandableText(text = post.description)

                Spacer(modifier = Modifier.height(SpaceMedium))

                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "${post.likeCount} Liked",
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp,
                        style = MaterialTheme.typography.h2
                    )

                    Text(
                        text = "${post.commentCount} comment(s)",
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp,
                        style = MaterialTheme.typography.h2
                    )
                }
            }
        }

        if (showProfileImage) {
            Image(
                painter = painterResource(id = R.drawable.bat_man_front),
                contentDescription = stringResource(R.string.profile_pic),
                modifier = Modifier
                    .size(ProfilePictureSizeMedium)
                    .clip(CircleShape)
                    .align(Alignment.TopCenter)
            )
        }
    }
}
