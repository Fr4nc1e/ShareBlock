package com.code.block.feature.profile.presentation.profilescreen.components.tablayout

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.items
import com.code.block.core.domain.model.Post
import com.code.block.core.presentation.components.PostCard
import com.code.block.core.presentation.components.Screen
import com.code.block.feature.profile.presentation.profilescreen.ProfileEvent
import com.code.block.feature.profile.presentation.profilescreen.ProfileViewModel

@Composable
fun ProfileCommentPostsScreen(
    posts: LazyPagingItems<Post>,
    onNavigate: (String) -> Unit = {}
) {
    val profileViewModel = hiltViewModel<ProfileViewModel>()

    LazyColumn(
        modifier = Modifier.fillMaxSize()
    ) {
        items(posts) { post ->
            PostCard(
                onNavigate = onNavigate,
                post = Post(
                    id = post?.id ?: "",
                    userId = post?.userId ?: "",
                    username = post?.username ?: "",
                    contentUrl = post?.contentUrl ?: "",
                    profilePictureUrl = post?.profilePictureUrl ?: "",
                    description = post?.description ?: "",
                    likeCount = post?.likeCount ?: 0,
                    commentCount = post?.commentCount ?: 0,
                    timestamp = (post?.timestamp ?: 0) as String,
                    isLiked = post?.isLiked ?: false,
                    isOwnPost = post?.isOwnPost ?: true
                ),
                onPostClick = { onNavigate(Screen.PostDetailScreen.route + "/${post?.id}") },
                onLikeClick = {
                    profileViewModel.onEvent(ProfileEvent.LikePost(postId = post?.id ?: ""))
                },
                comment = null,
                isProfileCommentScreen = true
            )
        }
    }
}
