package com.code.block.usecase.profile.components

import com.code.block.core.domain.resource.ProfileLikedPostResource
import com.code.block.feature.profile.domain.repository.ProfileRepository

class GetOwnPostsProfileUseCase(
    private val repository: ProfileRepository
) {
    suspend operator fun invoke(
        userId: String,
        page: Int
    ): ProfileLikedPostResource {
        return repository.getOwnPagedPosts(
            userId = userId,
            page = page
        )
    }
}
