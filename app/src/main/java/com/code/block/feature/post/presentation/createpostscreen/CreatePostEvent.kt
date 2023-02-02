package com.code.block.feature.post.presentation.createpostscreen

import android.net.Uri

sealed class CreatePostEvent {
    data class EnteredDescription(val description: String) : CreatePostEvent()
    data class InputContent(val contentUri: Uri?) : CreatePostEvent()
    object Post : CreatePostEvent()
}
