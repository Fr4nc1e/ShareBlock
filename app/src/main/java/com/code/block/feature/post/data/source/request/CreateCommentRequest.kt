package com.code.block.feature.post.data.source.request

data class CreateCommentRequest(
    val comment: String,
    val postId: String
)
