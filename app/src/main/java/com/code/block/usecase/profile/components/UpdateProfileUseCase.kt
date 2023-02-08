package com.code.block.usecase.profile.components

import android.net.Uri
import com.code.block.R
import com.code.block.core.domain.resource.Resource
import com.code.block.core.domain.resource.UpdateProfileResource
import com.code.block.core.util.ui.UiText
import com.code.block.feature.profile.domain.model.UpdateProfileData
import com.code.block.feature.profile.domain.repository.ProfileRepository

class UpdateProfileUseCase(
    private val repository: ProfileRepository,
) {
    suspend operator fun invoke(
        updateProfileData: UpdateProfileData,
        profilePictureUri: Uri?,
        bannerUri: Uri?,
    ): UpdateProfileResource {
        if (updateProfileData.username.isBlank()) {
            return Resource.Error(
                uiText = UiText.StringResource(R.string.error_username_empty),
            )
        }
        return repository.updateProfile(
            updateProfileData = updateProfileData,
            profilePictureUri = profilePictureUri,
            bannerImageUri = bannerUri,
        )
    }
}
