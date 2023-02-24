package com.code.block.core.util.ui.liker

import com.code.block.core.domain.model.Post
import com.code.block.core.domain.resource.Resource

class LikerImpl : Liker {
    override suspend fun clickLike(
        posts: List<Post>,
        parentId: String,
        onRequest: suspend (isLiked: Boolean) -> Resource<Unit>,
        onStateUpdate: (List<Post>) -> Unit
    ) {
        val post = posts.find { it.id == parentId }
        val currentlyLiked = post?.isLiked == true
        val currentLikeCount = post?.likeCount ?: 0
        val newPosts = posts.map { post1 ->
            if (post1.id == parentId) {
                post1.copy(
                    isLiked = !post1.isLiked,
                    likeCount = if (currentlyLiked) {
                        post1.likeCount - 1
                    } else {
                        post1.likeCount + 1
                    }
                )
            } else {
                post1
            }
        }
        onStateUpdate(newPosts)
        when (onRequest(currentlyLiked)) {
            is Resource.Success -> Unit
            is Resource.Error -> {
                val oldPosts = posts.map { post2 ->
                    if (post2.id == parentId) {
                        post2.copy(
                            isLiked = currentlyLiked,
                            likeCount = currentLikeCount
                        )
                    } else {
                        post2
                    }
                }
                onStateUpdate(oldPosts)
            }
        }
    }
}
