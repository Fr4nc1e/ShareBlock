package com.code.block.usecase.profile.components

import com.code.block.core.domain.resource.FollowedUsersResource
import com.code.block.feature.profile.domain.repository.ProfileRepository

class GetFollowersUseCase(
    private val repository: ProfileRepository
) {
    suspend operator fun invoke(userId: String): FollowedUsersResource {
        return repository.getFollowers(userId)
    }
}
