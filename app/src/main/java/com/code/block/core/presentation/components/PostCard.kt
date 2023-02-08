package com.code.block.core.presentation.components

import androidx.compose.animation.core.* // ktlint-disable no-wildcard-imports
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.* // ktlint-disable no-wildcard-imports
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.runtime.* // ktlint-disable no-wildcard-imports
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import com.code.block.core.domain.model.Post
import com.code.block.core.domain.state.LikedStates
import com.code.block.core.presentation.ui.theme.ProfilePictureSizeSmall
import com.code.block.core.presentation.ui.theme.SpaceSmall
import com.code.block.core.util.ui.ContentLoader

@Composable
fun PostCard(
    modifier: Modifier = Modifier,
    onNavigate: (String) -> Unit = {},
    post: Post,
    showDeleteIcon: Boolean = true,
    onPostClick: () -> Unit = {},
    onLikeClick: () -> Unit = {},
    onCommentClick: () -> Unit = {},
    onShareClick: () -> Unit = {},
    onDeleteClick: () -> Unit = {},
) {
    var transitionState by remember {
        mutableStateOf(MutableTransitionState(LikedStates.Disappeared))
    }

    Card(
        modifier = modifier,
        shape = RoundedCornerShape(16.dp),
        elevation = 5.dp,
    ) {
        Box {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(SpaceSmall)
                    .clickable { onPostClick() },
            ) {
                ActionRow(
                    post = post,
                    modifier = Modifier.fillMaxWidth(),
                    imageSize = ProfilePictureSizeSmall,
                    onUserClick = onNavigate,
                )

                Spacer(modifier = Modifier.height(SpaceSmall))

                ExpandableText(
                    text = post.description,
                    modifier = Modifier.padding(horizontal = SpaceSmall),
                )

                Spacer(modifier = Modifier.height(SpaceSmall))

                ContentLoader(
                    modifier = Modifier.pointerInput(Unit) {
                        detectTapGestures(
                            onTap = {
                                onPostClick()
                            },
                            onDoubleTap = {
                                transitionState = MutableTransitionState(LikedStates.Initial)
                                onLikeClick()
                            },
                        )
                    },
                    contentUrl = post.contentUrl,
                )

                Spacer(modifier = Modifier.height(SpaceSmall))

                InteractiveButtons(
                    post = post,
                    isLiked = post.isLiked,
                    showDeleteIcon = showDeleteIcon,
                    onNavigate = onNavigate,
                    onLikeClick = onLikeClick,
                    onCommentClick = onCommentClick,
                    onShareClick = onShareClick,
                    onDeleteClick = onDeleteClick,
                )
            }
            DoubleTapToLike(
                modifier = Modifier.align(Alignment.Center),
                transitionState = transitionState,
            )
        }
    }
}
