package com.code.block.usecase.post

import com.code.block.feature.post.domain.repository.PostRepository

class GetCommentsForPostUseCase(
    private val repository: PostRepository
) {
    suspend operator fun invoke(postId: String) =
        repository.getCommentsForPost(postId)
}
