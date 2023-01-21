package com.code.block.feature.profile.domain.repository

import android.net.Uri
import androidx.paging.PagingData
import com.code.block.core.domain.model.Post
import com.code.block.core.domain.util.ProfileResource
import com.code.block.core.domain.util.UpdateProfileResource
import com.code.block.feature.profile.domain.model.UpdateProfileData
import kotlinx.coroutines.flow.Flow

interface ProfileRepository {
    suspend fun getProfile(userId: String): ProfileResource
    fun getOwnPagedPosts(userId: String): Flow<PagingData<Post>>

    fun getLikedPosts(userId: String): Flow<PagingData<Post>>
    suspend fun updateProfile(
        updateProfileData: UpdateProfileData,
        bannerImageUri: Uri?,
        profilePictureUri: Uri?
    ): UpdateProfileResource
}
