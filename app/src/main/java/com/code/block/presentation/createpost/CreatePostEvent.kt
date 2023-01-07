package com.code.block.presentation.createpost

sealed class CreatePostEvent {
    data class EnteredDescription(val description: String) : CreatePostEvent()
    data class InputPicture(val imageUrl: String) : CreatePostEvent()
    object Post : CreatePostEvent()
}
