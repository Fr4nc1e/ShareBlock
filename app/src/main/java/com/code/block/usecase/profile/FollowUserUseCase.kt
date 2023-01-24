package com.code.block.usecase.profile

import com.code.block.core.domain.resource.FollowUpdateResource
import com.code.block.feature.profile.domain.repository.ProfileRepository

class FollowUserUseCase(
    private val profileRepository: ProfileRepository
) {
    suspend operator fun invoke(userId: String, isFollowing: Boolean): FollowUpdateResource {
        return if (isFollowing) {
            profileRepository.unfollowUser(userId)
        } else {
            profileRepository.followUser(userId)
        }
    }
}
