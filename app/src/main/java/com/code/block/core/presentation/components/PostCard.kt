package com.code.block.core.presentation.components

import androidx.compose.foundation.Image
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.code.block.R
import com.code.block.core.domain.model.Comment
import com.code.block.core.domain.model.Post
import com.code.block.core.presentation.ui.theme.ProfilePictureSizeExtraSmall
import com.code.block.core.presentation.ui.theme.ProfilePictureSizeSmall
import com.code.block.core.presentation.ui.theme.SpaceSmall
import com.code.block.core.presentation.ui.theme.quicksand
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Composable
fun PostCard(
    modifier: Modifier = Modifier,
    navigator: DestinationsNavigator,
    post: Post,
    comment: Comment = Comment(
        username = "test",
        comment = "actually we have credit card etc information that we want to store and use them in espresso tests. As its critical information, we neither want it to be stored in the main project file nor want to delete it every time when we build a release build apk",
        profilePictureUrl = R.drawable.icons8_superman_144,
        formattedTime = System.currentTimeMillis().toString()
    ),
    isProfileCommentScreen: Boolean = false,
    onPostClick: () -> Unit = {}
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .border(
                width = 1.dp,
                color = MaterialTheme.colors.surface
            )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(SpaceSmall)
                .clickable {
                    onPostClick()
                }
        ) {
            ActionRow(
                post = post,
                modifier = Modifier.fillMaxWidth(),
                imageSize = ProfilePictureSizeSmall
            )

            Spacer(modifier = Modifier.height(SpaceSmall))

            ExpandableText(
                text = post.description,
                modifier = Modifier.padding(horizontal = SpaceSmall)
            )

            Spacer(modifier = Modifier.height(SpaceSmall))

            Image(
                painter = rememberAsyncImagePainter(post.contentUrl),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .padding(horizontal = SpaceSmall)
                    .fillMaxWidth()
                    .aspectRatio(16f / 9f)
                    .clip(RoundedCornerShape(10.dp))
            )

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(MaterialTheme.shapes.medium)
            ) {
                Spacer(modifier = Modifier.height(SpaceSmall))

                InteractiveButtons(
                    post = post,
                    navigator = navigator
                )

                if (isProfileCommentScreen) {
                    Box(
                        modifier = Modifier
                            .padding(
                                horizontal = SpaceSmall
                            )
                            .fillMaxWidth()
                    ) {
                        Row(
                            modifier = Modifier.fillMaxSize(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Image(
                                painter = painterResource(id = comment.profilePictureUrl),
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
                            )

                            Spacer(modifier = Modifier.width(SpaceSmall))

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
                        }
                    }
                }
            }
        }
    }
}
