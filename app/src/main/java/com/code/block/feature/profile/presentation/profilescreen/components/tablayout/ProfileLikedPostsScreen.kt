package com.code.block.feature.profile.presentation.profilescreen.components.tablayout

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.items
import com.code.block.core.domain.model.Post
import com.code.block.core.presentation.components.PostCard
import com.code.block.core.presentation.components.Screen

@Composable
fun ProfileLikedPostsScreen(
    posts: LazyPagingItems<Post>,
    onNavigate: (String) -> Unit = {}
) {
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
                    contentUrl = "http://172.28.211.51:8081/post_contents/" +
                        post?.contentUrl?.takeLastWhile { it != '/' },
                    profilePictureUrl = post?.profilePictureUrl ?: "",
                    description = post?.description ?: "",
                    likeCount = post?.likeCount ?: 0,
                    commentCount = post?.commentCount ?: 0,
                    timestamp = (post?.timestamp ?: 0) as String,
                    isLiked = post?.isLiked ?: true,
                    isOwnPost = post?.isOwnPost ?: true
                ),
                comment = null,
                onPostClick = { onNavigate(Screen.PostDetailScreen.route + "/${post?.id}") }
            )
        }
    }
}
