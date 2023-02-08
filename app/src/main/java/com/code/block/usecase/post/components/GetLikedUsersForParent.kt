package com.code.block.usecase.post.components

import com.code.block.core.domain.resource.LikedUsersResource
import com.code.block.feature.post.domain.repository.PostRepository

class GetLikedUsersForParent(
    private val repository: PostRepository,
) {
    suspend operator fun invoke(
        parentId: String,
    ): LikedUsersResource {
        return repository.getLikedUsers(parentId)
    }
}
