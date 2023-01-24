package com.code.block.feature.profile.presentation.profilescreen.components.tablayout

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.code.block.core.domain.model.Post
import com.code.block.core.domain.state.PageState
import com.code.block.core.presentation.components.PostCard
import com.code.block.core.presentation.components.Screen
import com.code.block.feature.profile.presentation.profilescreen.ProfileEvent
import com.code.block.feature.profile.presentation.profilescreen.ProfileViewModel

@Composable
fun ProfileLikedPostsScreen(
    likedPagingState: PageState<Post>,
    onNavigate: (String) -> Unit = {}
) {
    val profileViewModel = hiltViewModel<ProfileViewModel>()

    LazyColumn(
        modifier = Modifier.fillMaxSize()
    ) {
        items(likedPagingState.items.size) { i ->
            val post = likedPagingState.items[i]
            if (i >= likedPagingState.items.size - 1 &&
                !likedPagingState.endReached &&
                !likedPagingState.isLoading
            ) {
                profileViewModel.loadLikedPosts()
            }
            PostCard(
                onNavigate = onNavigate,
                post = post,
                comment = null,
                onPostClick = { onNavigate(Screen.PostDetailScreen.route + "/${post.id}") },
                onLikeClick = {
                    profileViewModel.onEvent(ProfileEvent.LikePageLikePost(postId = post.id))
                }
            )
        }
        item {
            Spacer(modifier = Modifier.height(90.dp))
        }
    }
}
