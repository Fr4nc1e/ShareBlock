package com.code.block.feature.profile.presentation.profilescreen.components.tablayout

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.code.block.core.domain.model.Post
import com.code.block.core.presentation.components.PostCard
import com.code.block.core.utils.Screen

@Composable
fun ProfilePostScreen(
    post: Post,
    onNavigate: (String) -> Unit = {}
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize()
    ) {
        items(6) {
            PostCard(
                onNavigate = onNavigate,
                post = post,
                onPostClick = {
                    onNavigate(Screen.PostDetailScreen.route)
                }
            )
        }
    }
}
