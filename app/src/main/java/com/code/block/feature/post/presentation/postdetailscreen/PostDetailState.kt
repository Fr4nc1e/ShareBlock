package com.code.block.feature.post.presentation.postdetailscreen

import com.code.block.core.domain.model.Comment
import com.code.block.core.domain.model.Post

data class PostDetailState(
    val post: Post? = null,
    val comments: List<Comment> = emptyList(),
    val isLoadingPost: Boolean = false,
    val isLoadingComments: Boolean = false
)
