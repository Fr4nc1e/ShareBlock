package com.code.block.feature.post.domain.usecase

import com.code.block.R
import com.code.block.core.domain.util.CreateCommentResource
import com.code.block.core.domain.util.Resource
import com.code.block.core.util.UiText
import com.code.block.feature.post.domain.repository.PostRepository

class CreateCommentUseCase(
    private val postRepository: PostRepository
) {
    suspend operator fun invoke(
        postId: String,
        comment: String
    ): CreateCommentResource {
        if (comment.isBlank()) {
            return Resource.Error(uiText = UiText.StringResource(R.string.unknown_error))
        }
        if (postId.isBlank()) {
            return Resource.Error(uiText = UiText.unknownError())
        }
        return postRepository.createComment(
            comment = comment,
            postId = postId
        )
    }
}
