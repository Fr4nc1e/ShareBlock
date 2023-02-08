package com.code.block.feature.post.presentation.createpostscreen

data class CreatePostState(
    val description: String = "",
    val imageUrl: String = "",
    val postError: PostError? = null,
) {
    sealed class PostError
}
