package com.code.block.feature.profile.domain.repository

import android.net.Uri
import com.code.block.core.domain.resource.* // ktlint-disable no-wildcard-imports
import com.code.block.core.util.Constants
import com.code.block.feature.profile.domain.model.UpdateProfileData

interface ProfileRepository {
    suspend fun getProfile(userId: String): ProfileResource
    suspend fun getOwnPagedPosts(
        userId: String,
        page: Int,
        pageSize: Int = Constants.PAGE_SIZE_POSTS
    ): ProfileOwnPostResource
    suspend fun getLikedPosts(
        userId: String,
        page: Int,
        pageSize: Int = Constants.PAGE_SIZE_POSTS
    ): ProfileLikedPostResource
    suspend fun updateProfile(
        updateProfileData: UpdateProfileData,
        bannerImageUri: Uri?,
        profilePictureUri: Uri?
    ): UpdateProfileResource
    suspend fun searchUser(query: String): SearchResource
    suspend fun followUser(userId: String): FollowUpdateResource
    suspend fun unfollowUser(userId: String): FollowUpdateResource
    suspend fun getComments(
        page: Int,
        pageSize: Int = Constants.PAGE_SIZE_POSTS
    ): CommentsForUserResource
    fun logout()
}
