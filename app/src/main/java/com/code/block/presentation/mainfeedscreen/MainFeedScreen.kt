package com.code.block.presentation.mainfeedscreen

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.code.block.R
import com.code.block.domain.model.Post
import com.code.block.presentation.components.PostCard
import com.code.block.presentation.destinations.PostDetailScreenDestination
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Destination
@Composable
fun MainFeedScreen(
    navigator: DestinationsNavigator
) {
    PostCard(
        post = Post(
            username = "Bat Man",
            imageUrl = "",
            profilePictureUrl = "",
            description = stringResource(id = R.string.test_string),
            likeCount = 17,
            commentCount = 7
        ),
        onPostClick = {
            navigator.navigate(PostDetailScreenDestination)
        }
    )
}
