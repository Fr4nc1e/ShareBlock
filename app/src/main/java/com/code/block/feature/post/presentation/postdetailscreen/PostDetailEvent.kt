package com.code.block.feature.post.presentation.postdetailscreen

sealed class PostDetailEvent {
    object LikePost : PostDetailEvent()
    data class EnteredComment(val comment: String) : PostDetailEvent()
    data class LikeComment(val commentId: String) : PostDetailEvent()
    object SharePost : PostDetailEvent()
    object Comment : PostDetailEvent()
}
