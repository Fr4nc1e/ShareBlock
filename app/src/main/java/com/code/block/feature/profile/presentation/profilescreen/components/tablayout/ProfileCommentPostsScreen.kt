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
fun ProfileCommentPostsScreen(
    ownPagingState: PageState<Post>,
    onNavigate: (String) -> Unit = {}
) {
    val profileViewModel = hiltViewModel<ProfileViewModel>()

    LazyColumn(
        modifier = Modifier.fillMaxSize()
    ) {
        items(ownPagingState.items.size) { i ->
            val post = ownPagingState.items[i]
            if (i >= ownPagingState.items.size - 1 &&
                !ownPagingState.endReached &&
                !ownPagingState.isLoading
            ) {
                profileViewModel.loadOwnPosts()
            }
            PostCard(
                onNavigate = onNavigate,
                post = post,
                onPostClick = { onNavigate(Screen.PostDetailScreen.route + "/${post.id}") },
                onLikeClick = {
                    profileViewModel.onEvent(ProfileEvent.OwnPageLikePost(postId = post.id))
                },
                comment = null,
                isProfileCommentScreen = true
            )
        }
        item {
            Spacer(modifier = Modifier.height(90.dp))
        }
    }
}
