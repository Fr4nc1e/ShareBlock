package com.code.block.presentation.createpost

data class CreatePostState(
    val description: String = "",
    val imageUrl: String = "",
    val postError: PostError? = null
) {
    sealed class PostError
}
