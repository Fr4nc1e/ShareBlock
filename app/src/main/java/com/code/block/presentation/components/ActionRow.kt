package com.code.block.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.* // ktlint-disable no-wildcard-imports
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush.Companion.horizontalGradient
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.code.block.R
import com.code.block.domain.model.Post
import com.code.block.ui.theme.ProfilePictureSizeExtraSmall
import com.code.block.ui.theme.SpaceSmall

@Composable
fun ActionRow(
    modifier: Modifier = Modifier,
    post: Post,
    imageSize: Dp = ProfilePictureSizeExtraSmall,
    onUserClick: (Post) -> Unit = {}
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = CenterVertically
    ) {
        Row(
            modifier = Modifier
                .clickable {
                    onUserClick(post)
                }
        ) {
            Image(
                painter = painterResource(id = post.profilePictureUrl),
                contentDescription = stringResource(R.string.profile_pic),
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(imageSize)
                    .clip(CircleShape)
                    .border(
                        width = 1.dp,
                        brush = horizontalGradient(
                            listOf(
                                Color.Green,
                                Color.Blue,
                                Color.Transparent
                            )
                        ),
                        shape = CircleShape
                    )
                    .align(CenterVertically)
            )
            Spacer(modifier = Modifier.width(SpaceSmall))
            Text(
                text = post.username,
                style = MaterialTheme.typography.body1,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colors.primary,
                modifier = Modifier
                    .align(CenterVertically)
            )
        }

        Text(
            text = "2 days ago",
            style = MaterialTheme.typography.body2,
            modifier = Modifier
                .align(CenterVertically)
        )
    }
}
