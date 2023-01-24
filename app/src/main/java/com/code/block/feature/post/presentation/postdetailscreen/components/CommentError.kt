package com.code.block.feature.post.presentation.postdetailscreen.components

import com.code.block.core.domain.util.Error

sealed class CommentError : Error() {
    object FieldEmpty : CommentError()
}
