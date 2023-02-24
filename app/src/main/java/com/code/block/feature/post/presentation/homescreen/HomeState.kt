package com.code.block.feature.post.presentation.homescreen

data class HomeState(
    val isLoadingFirstTime: Boolean = true,
    val isLoadingNewPosts: Boolean = false
)
