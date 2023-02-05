package com.code.block.usecase.profile.components

import com.code.block.core.domain.resource.CommentsForUserResource
import com.code.block.feature.profile.domain.repository.ProfileRepository

class GetCommentsUseCase(
    private val repository: ProfileRepository
) {
    suspend operator fun invoke(
        userId: String,
        page: Int
    ): CommentsForUserResource {
        return repository.getComments(
            userId = userId,
            page = page
        )
    }
}
