package com.code.block.core.util

import androidx.compose.runtime.MutableState
import com.code.block.feature.post.presentation.postdetailscreen.state.UserInfoState
import com.code.block.usecase.profile.ProfileUseCases

class UserInfoProvider(
    private val profileUseCases: ProfileUseCases
) {

    suspend fun provideUserInfo(
        userId: String,
        userInfoState: MutableState<UserInfoState>
    ) {
        profileUseCases.getProfileUseCase(
            userId
        ).data?.toUserInfoState()?.apply {
            userInfoState.value = userInfoState.value.copy(
                username = this.username,
                profilePictureUrl = this.profilePictureUrl
            )
        }
    }
}
