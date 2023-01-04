package com.code.block.presentation.mainfeedscreen

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.code.block.R
import com.code.block.domain.model.Post
import com.code.block.presentation.components.PostCard
import com.ramcosta.composedestinations.annotation.Destination

@Destination
@Composable
fun MainFeedScreen() {
    PostCard(
        post = Post(
            username = "hello",
            imageUrl = "",
            profilePictureUrl = "",
            description = stringResource(R.string.test_string),
            likeCount = 12,
            commentCount = 12
        )
    )
}
