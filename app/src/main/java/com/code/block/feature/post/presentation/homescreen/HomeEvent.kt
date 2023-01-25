package com.code.block.feature.post.presentation.homescreen

sealed class HomeEvent {
    data class LikedParent(val postId: String) : HomeEvent()
    object Refresh : HomeEvent()
}
