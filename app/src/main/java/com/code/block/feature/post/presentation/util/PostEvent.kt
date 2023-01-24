package com.code.block.feature.post.presentation.util

import com.code.block.core.domain.util.Event

sealed class PostEvent : Event() {
    object OnLiked : PostEvent()
}
