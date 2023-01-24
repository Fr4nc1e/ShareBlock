package com.code.block.usecase.post

import com.code.block.core.domain.resource.LikeUpdateResource
import com.code.block.feature.post.domain.repository.PostRepository

class LikeParentUseCase(
    private val repository: PostRepository
) {
    suspend operator fun invoke(
        parentId: String,
        parentType: Int,
        isLiked: Boolean
    ): LikeUpdateResource {
        return if (isLiked) {
            repository.unlikeParent(
                parentId = parentId,
                parentType = parentType
            )
        } else {
            repository.likeParent(
                parentId = parentId,
                parentType = parentType
            )
        }
    }
}
