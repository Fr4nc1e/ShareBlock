package com.code.block.feature.post.presentation.homescreen

import com.code.block.core.domain.model.Post

sealed class HomeEvent {
    data class LikedParent(val postId: String) : HomeEvent()
    data class DeletePost(val post: Post) : HomeEvent()
    object Refresh : HomeEvent()
}
