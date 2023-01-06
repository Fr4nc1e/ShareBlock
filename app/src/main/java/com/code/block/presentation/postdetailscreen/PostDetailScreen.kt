package com.code.block.presentation.postdetailscreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.* // ktlint-disable no-wildcard-imports
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.code.block.R
import com.code.block.domain.model.Post
import com.code.block.domain.util.DateFormattedUtil
import com.code.block.presentation.components.ActionRow
import com.code.block.presentation.components.InteractiveButtons
import com.code.block.presentation.components.StandardTopBar
import com.code.block.presentation.postdetailscreen.components.Comment
import com.code.block.ui.theme.ProfilePictureSizeSmall
import com.code.block.ui.theme.SpaceLarge
import com.code.block.ui.theme.SpaceMedium
import com.code.block.ui.theme.SpaceSmall
import com.ramcosta.composedestinations.annotation.Destination

@Destination
@Composable
fun PostDetailScreen(
    post: Post = Post(
        username = "Batman",
        imageUrl = R.drawable.hd_batman,
        profilePictureUrl = R.drawable.batman_profile_image,
        description = stringResource(id = R.string.test_string),
        likeCount = 17,
        commentCount = 7,
        formattedTime = DateFormattedUtil
            .timestampToFormattedString(
                timestamp = System.currentTimeMillis(),
                pattern = "MMM dd, HH:mm"
            )
    )
) {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        StandardTopBar(
            title = {
                Text(
                    text = stringResource(R.string.your_feed),
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colors.onBackground
                )
            },
            modifier = Modifier.fillMaxWidth()
        )

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colors.background)
        ) {
            item {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(SpaceSmall)
                        .background(MaterialTheme.colors.background)
                ) {
                    ActionRow(
                        post = post,
                        imageSize = ProfilePictureSizeSmall,
                        modifier = Modifier.fillMaxWidth()
                    )

                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .clip(MaterialTheme.shapes.medium)
                            .shadow(5.dp)
                            .background(MaterialTheme.colors.background)
                    ) {
                        Text(
                            text = post.description,
                            style = MaterialTheme.typography.body1
                        )

                        Spacer(modifier = Modifier.height(SpaceMedium))

                        Image(
                            painter = painterResource(id = post.imageUrl),
                            contentDescription = stringResource(id = R.string.post),
                            modifier = Modifier
                                .fillMaxWidth()
                                .aspectRatio(16f / 9f)
                                .clip(RoundedCornerShape(10.dp))
                        )

                        Spacer(modifier = Modifier.height(SpaceMedium))

                        Divider(
                            color = MaterialTheme.colors.surface,
                            thickness = 2.dp
                        )

                        Spacer(modifier = Modifier.height(SpaceSmall))

                        InteractiveButtons(post = post)

                        Spacer(modifier = Modifier.height(SpaceSmall))

                        Divider(
                            color = MaterialTheme.colors.surface,
                            thickness = 2.dp
                        )
                    }
                }
            }

            items(20) {
                Comment(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            horizontal = SpaceLarge,
                            vertical = SpaceSmall
                        ),
                    comment = com.code.block.domain.model.Comment(
                        username = "Superman",
                        comment = stringResource(R.string.test_string_superman),
                        profilePictureUrl = R.drawable.superman_batman,
                        formattedTime = DateFormattedUtil
                            .timestampToFormattedString(
                                timestamp = System.currentTimeMillis(),
                                pattern = "MMM dd, HH:mm"
                            )
                    )
                )
            }
        }
    }
}
