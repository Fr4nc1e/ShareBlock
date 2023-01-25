package com.code.block.usecase.profile

data class ProfileUseCases(
    val getProfileUseCase: GetProfileUseCase,
    val updateProfileUseCase: UpdateProfileUseCase,
    val getOwnPostsProfileUseCase: GetOwnPostsProfileUseCase,
    val getLikedPostsProfileUseCase: GetLikedPostsProfileUseCase,
    val searchUseCase: SearchUseCase,
    val followUserUseCase: FollowUserUseCase,
    val commentsUseCase: GetCommentsUseCase
)
