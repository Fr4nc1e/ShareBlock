package com.code.block.feature.profile.presentation.profilescreen.components.tablayout

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.code.block.core.domain.model.Comment
import com.code.block.core.domain.state.PageState
import com.code.block.core.presentation.components.Screen
import com.code.block.core.presentation.ui.theme.SpaceSmall
import com.code.block.feature.post.presentation.postdetailscreen.components.CommentItem
import com.code.block.feature.profile.presentation.profilescreen.ProfileEvent
import com.code.block.feature.profile.presentation.profilescreen.ProfileViewModel

@Composable
fun ProfileCommentPostsScreen(
    commentPagingState: PageState<Comment>,
    onNavigate: (String) -> Unit = {}
) {
    val profileViewModel = hiltViewModel<ProfileViewModel>()

    LazyColumn(
        modifier = Modifier.fillMaxSize()
    ) {
        items(commentPagingState.items.size) { i ->
            val comment = commentPagingState.items[i]
            if (i >= commentPagingState.items.size - 1 &&
                !commentPagingState.endReached &&
                !commentPagingState.isLoading
            ) {
                profileViewModel.loadComments()
            }
            CommentItem(
                comment = comment,
                modifier = Modifier.padding(SpaceSmall),
                onUserClick = onNavigate,
                onLikeClick = {
                    profileViewModel.onEvent(ProfileEvent.LikeComment(commentId = comment.id))
                },
                onItemClick = {
                    onNavigate(Screen.PostDetailScreen.route + "/${comment.postId}")
                }
            )
        }
        item {
            Spacer(modifier = Modifier.height(90.dp))
        }
    }
}
