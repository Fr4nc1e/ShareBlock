package com.code.block.usecase.profile

import com.code.block.core.domain.resource.CommentsForUserResource
import com.code.block.feature.profile.domain.repository.ProfileRepository

class GetCommentsUseCase(
    private val repository: ProfileRepository
) {
    suspend operator fun invoke(page: Int): CommentsForUserResource {
        return repository.getComments(page = page)
    }
}
