package com.code.block.core.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.* // ktlint-disable no-wildcard-imports
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.code.block.core.domain.model.Post
import com.code.block.core.presentation.ui.theme.ProfilePictureSizeSmall
import com.code.block.core.presentation.ui.theme.SpaceSmall
import com.code.block.core.util.ui.ContentLoader

@Composable
fun PostCard(
    modifier: Modifier = Modifier,
    onNavigate: (String) -> Unit = {},
    post: Post,
    onPostClick: () -> Unit = {},
    onLikeClick: () -> Unit = {},
    onCommentClick: () -> Unit = {},
    onShareClick: () -> Unit = {}
) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(16.dp),
        elevation = 5.dp
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(SpaceSmall)
                .clickable {
                    onPostClick()
                }
        ) {
            ActionRow(
                post = post,
                modifier = Modifier.fillMaxWidth(),
                imageSize = ProfilePictureSizeSmall,
                onUserClick = onNavigate
            )

            Spacer(modifier = Modifier.height(SpaceSmall))

            ExpandableText(
                text = post.description,
                modifier = Modifier.padding(horizontal = SpaceSmall)
            )

            Spacer(modifier = Modifier.height(SpaceSmall))

            ContentLoader(contentUrl = post.contentUrl)

            Spacer(modifier = Modifier.height(SpaceSmall))

            InteractiveButtons(
                post = post,
                isLiked = post.isLiked,
                onNavigate = onNavigate,
                onLikeClick = onLikeClick,
                onCommentClick = onCommentClick,
                onShareClick = onShareClick
            )
        }
    }
}
