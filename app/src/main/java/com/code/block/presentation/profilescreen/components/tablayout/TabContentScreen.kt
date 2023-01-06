package com.code.block.presentation.profilescreen.components.tablayout

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.code.block.domain.model.Post
import com.code.block.presentation.destinations.PostDetailScreenDestination
import com.code.block.presentation.mainfeedscreen.components.PostCard
import com.code.block.ui.theme.ProfilePictureSizeLarge
import com.code.block.ui.theme.SpaceSmall
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Composable
fun TabContentScreen(
    post: Post,
    navigator: DestinationsNavigator
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize()
    ) {
        items(20) {
            PostCard(
                post = post,
                modifier = Modifier
                    .offset(y = -ProfilePictureSizeLarge / 2f - SpaceSmall),
                onPostClick = {
                    navigator.navigate(PostDetailScreenDestination)
                }
            )
        }
    }
}
