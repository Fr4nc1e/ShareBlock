package com.code.block.feature.profile.domain.repository

import android.net.Uri
import com.code.block.core.domain.util.ProfileResource
import com.code.block.core.domain.util.UpdateProfileResource
import com.code.block.feature.profile.domain.model.UpdateProfileData

interface ProfileRepository {
    suspend fun getProfile(userId: String): ProfileResource

    suspend fun updateProfile(
        updateProfileData: UpdateProfileData,
        bannerImageUri: Uri?,
        profilePictureUri: Uri?
    ): UpdateProfileResource
}
