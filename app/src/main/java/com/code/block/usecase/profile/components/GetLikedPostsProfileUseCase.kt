package com.code.block.usecase.profile.components

import com.code.block.core.domain.resource.ProfileLikedPostResource
import com.code.block.feature.profile.domain.repository.ProfileRepository

class GetLikedPostsProfileUseCase(
    private val repository: ProfileRepository
) {
    suspend operator fun invoke(
        userId: String,
        page: Int
    ): ProfileLikedPostResource {
        return repository.getLikedPosts(
            userId = userId,
            page = page
        )
    }
}
