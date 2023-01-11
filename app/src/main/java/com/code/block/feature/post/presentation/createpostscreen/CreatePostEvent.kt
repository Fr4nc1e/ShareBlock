package com.code.block.feature.post.presentation.createpostscreen

sealed class CreatePostEvent {
    data class EnteredDescription(val description: String) : CreatePostEvent()
    data class InputPicture(val imageUrl: String) : CreatePostEvent()
    object Post : CreatePostEvent()
}
