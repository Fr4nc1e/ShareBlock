package com.code.block.usecase.post.components

import com.code.block.R
import com.code.block.core.domain.resource.CreateCommentResource
import com.code.block.core.domain.resource.Resource
import com.code.block.core.util.ui.UiText
import com.code.block.feature.post.domain.repository.PostRepository

class CreateCommentUseCase(
    private val repository: PostRepository
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
        return repository.createComment(
            comment = comment,
            postId = postId
        )
    }
}
