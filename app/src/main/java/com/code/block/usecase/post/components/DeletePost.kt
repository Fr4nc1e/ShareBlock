package com.code.block.usecase.post.components

import com.code.block.core.domain.resource.DeleteResource
import com.code.block.feature.post.domain.repository.PostRepository

class DeletePost(
    private val repository: PostRepository,
) {

    suspend operator fun invoke(postId: String): DeleteResource {
        return repository.deletePost(postId)
    }
}
