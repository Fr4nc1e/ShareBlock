package com.code.block.feature.post.domain.usecase

import com.code.block.core.domain.util.LikedUsersResource
import com.code.block.feature.post.domain.repository.PostRepository

class GetLikedUsersForParent(
    private val repository: PostRepository
) {
    suspend operator fun invoke(
        parentId: String
    ): LikedUsersResource {
        return repository.getLikedUsers(parentId)
    }
}
