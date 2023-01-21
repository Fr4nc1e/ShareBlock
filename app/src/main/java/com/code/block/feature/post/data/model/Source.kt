package com.code.block.feature.post.data.model

sealed class Source {
    object PostsForFollow : Source()
    data class OwnPosts(val userId: String) : Source()
    data class CommentedPosts(val userId: String) : Source()
    data class LikedPosts(val userId: String) : Source()
}
