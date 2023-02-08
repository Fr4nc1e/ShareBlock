package com.code.block.usecase.profile.components

import com.code.block.core.domain.resource.FollowingUsersResource
import com.code.block.feature.profile.domain.repository.ProfileRepository

class GetFollowingsUseCase(
    private val repository: ProfileRepository,
) {
    suspend operator fun invoke(userId: String): FollowingUsersResource {
        return repository.getFollowings(userId)
    }
}
