package com.code.block.feature.post.presentation.homescreen

sealed class HomeEvent {
    object LoadPosts : HomeEvent()
    object LoadPage : HomeEvent()
    object Refresh : HomeEvent()
    data class LikedParent(val postId: String) : HomeEvent()
}
