package com.code.block.feature.profile.domain.usecase

data class ProfileUseCases(
    val getProfileUseCase: GetProfileUseCase,
    val updateProfileUseCase: UpdateProfileUseCase,
    val getOwnPostsProfileUseCase: GetOwnPostsProfileUseCase,
    val getLikedPostsProfileUseCase: GetLikedPostsProfileUseCase
)
