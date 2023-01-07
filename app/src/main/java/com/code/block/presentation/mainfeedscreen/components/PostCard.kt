package com.code.block.presentation.mainfeedscreen.components

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
import com.code.block.presentation.components.ActionRow
import com.code.block.presentation.components.ExpandableText
import com.code.block.presentation.components.InteractiveButtons
import com.code.block.ui.theme.* // ktlint-disable no-wildcard-imports
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Composable
fun PostCard(
    navigator: DestinationsNavigator,
    post: Post,
    modifier: Modifier = Modifier,
    onPostClick: () -> Unit = {}
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(SpaceSmall)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .clip(MaterialTheme.shapes.medium)
                .shadow(5.dp)
                .background(MediumGray)

        ) {
            Image(
                painter = painterResource(id = post.imageUrl),
                contentDescription = stringResource(
                    id = R.string.front_image_sample
                ),
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(16f / 9f)
            )

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

                ExpandableText(text = post.description)

                Spacer(modifier = Modifier.height(SpaceSmall))

                InteractiveButtons(
                    post = post,
                    navigator = navigator
                )
            }
        }
    }
}
