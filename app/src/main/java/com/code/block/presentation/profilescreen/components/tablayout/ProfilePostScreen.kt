package com.code.block.presentation.profilescreen.components.tablayout

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.code.block.domain.model.Post
import com.code.block.presentation.destinations.PostDetailScreenDestination
import com.code.block.presentation.mainfeedscreen.components.PostCard
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Composable
fun ProfilePostScreen(
    post: Post,
    navigator: DestinationsNavigator
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize()
    ) {
        items(6) {
            PostCard(
                navigator = navigator,
                post = post,
                onPostClick = {
                    navigator.navigate(PostDetailScreenDestination)
                }
            )
        }
    }
}
