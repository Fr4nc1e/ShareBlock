package com.code.block.feature.profile.domain.usecase

import android.net.Uri
import com.code.block.R
import com.code.block.core.domain.util.Resource
import com.code.block.core.domain.util.UpdateProfileResource
import com.code.block.core.util.UiText
import com.code.block.feature.profile.domain.model.UpdateProfileData
import com.code.block.feature.profile.domain.repository.ProfileRepository

class UpdateProfileUseCase(
    private val repository: ProfileRepository
) {
    suspend operator fun invoke(
        updateProfileData: UpdateProfileData,
        profilePictureUri: Uri?,
        bannerUri: Uri?
    ): UpdateProfileResource {
        if (updateProfileData.username.isBlank()) {
            return Resource.Error(
                uiText = UiText.StringResource(R.string.error_username_empty)
            )
        }
//        val isValidGithubUrl = "(https://)?(www\\.)?github\\.com/[A-z0-9_-]+/?"
//            .toRegex()
//            .matches(updateProfileData.gitHubUrl)
//        if (!isValidGithubUrl) {
//            return Resource.Error(
//                uiText = UiText.StringResource(R.string.error_invalid_github_url)
//            )
//        }
        return repository.updateProfile(
            updateProfileData = updateProfileData,
            profilePictureUri = profilePictureUri,
            bannerImageUri = bannerUri
        )
    }
}
