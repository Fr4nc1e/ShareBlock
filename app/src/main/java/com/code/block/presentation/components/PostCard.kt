package com.code.block.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.* // ktlint-disable no-wildcard-imports
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.* // ktlint-disable no-wildcard-imports
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.code.block.R
import com.code.block.domain.model.Post
import com.code.block.ui.theme.* // ktlint-disable no-wildcard-imports

@Composable
fun PostCard(
    post: Post,
    modifier: Modifier = Modifier,
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
                .clip(MaterialTheme.shapes.medium)
                .shadow(5.dp)
                .background(MediumGray)
        ) {
            Image(
                painter = painterResource(id = R.drawable.hd_batman),
                contentDescription = stringResource(
                    id = R.string.front_image_sample
                ),
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(16f / 9f)
                    .clickable {
                        onPostClick()
                    }
            )

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(SpaceSmall)
            ) {
                ActionRow(
                    post = post,
                    modifier = Modifier.fillMaxWidth(),
                    onUserClick = {}
                )

                Spacer(modifier = Modifier.height(SpaceSmall))

                ExpandableText(text = post.description)

                Spacer(modifier = Modifier.height(SpaceSmall))

                InteractiveButtons(post = post)
            }
        }
    }
}
