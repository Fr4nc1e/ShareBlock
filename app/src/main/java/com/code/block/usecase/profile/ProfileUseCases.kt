package com.code.block.usecase.profile

import com.code.block.usecase.profile.components.* // ktlint-disable no-wildcard-imports

data class ProfileUseCases(
    val getProfileUseCase: GetProfileUseCase,
    val updateProfileUseCase: UpdateProfileUseCase,
    val getOwnPostsProfileUseCase: GetOwnPostsProfileUseCase,
    val getLikedPostsProfileUseCase: GetLikedPostsProfileUseCase,
    val searchUseCase: SearchUseCase,
    val followUserUseCase: FollowUserUseCase,
    val commentsUseCase: GetCommentsUseCase,
    val logoutUseCase: LogoutUseCase,
    val getFollowersUseCase: GetFollowersUseCase,
    val getFollowingsUseCase: GetFollowingsUseCase,
)
