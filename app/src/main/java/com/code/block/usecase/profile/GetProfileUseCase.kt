package com.code.block.usecase.profile

import com.code.block.core.domain.resource.ProfileResource
import com.code.block.feature.profile.domain.repository.ProfileRepository

class GetProfileUseCase(
    private val repository: ProfileRepository
) {
    suspend operator fun invoke(userId: String): ProfileResource {
        return repository.getProfile(userId = userId)
    }
}
