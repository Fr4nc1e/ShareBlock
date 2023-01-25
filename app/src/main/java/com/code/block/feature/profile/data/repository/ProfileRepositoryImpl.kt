package com.code.block.feature.profile.data.repository

import android.net.Uri
import androidx.core.net.toFile
import com.code.block.R
import com.code.block.core.domain.resource.* // ktlint-disable no-wildcard-imports
import com.code.block.core.util.ui.UiText
import com.code.block.feature.post.data.source.PostApi
import com.code.block.feature.profile.data.source.ProfileApi
import com.code.block.feature.profile.data.source.request.FollowUpdateRequest
import com.code.block.feature.profile.domain.model.UpdateProfileData
import com.code.block.feature.profile.domain.repository.ProfileRepository
import com.google.gson.Gson
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import retrofit2.HttpException
import java.io.IOException

class ProfileRepositoryImpl(
    private val profileApi: ProfileApi,
    private val postApi: PostApi,
    private val gson: Gson
) : ProfileRepository {
    override suspend fun getProfile(userId: String): ProfileResource {
        return try {
            val response = profileApi.getUserProfile(userId)
            if (response.successful) {
                Resource.Success(data = response.data?.toProfile(), uiText = null)
            } else {
                response.message?.let {
                    Resource.Error(uiText = UiText.CallResponseText(value = it))
                } ?: Resource.Error(uiText = UiText.StringResource(R.string.unknown_error))
            }
        } catch (e: IOException) {
            Resource.Error(
                uiText = UiText.StringResource(R.string.fail_to_connect)
            )
        } catch (e: HttpException) {
            Resource.Error(
                uiText = UiText.StringResource(R.string.fail_to_connect)
            )
        }
    }

    override suspend fun updateProfile(
        updateProfileData: UpdateProfileData,
        bannerImageUri: Uri?,
        profilePictureUri: Uri?
    ): UpdateProfileResource {
        val bannerFile = bannerImageUri?.toFile()
        val profilePictureFile = profilePictureUri?.toFile()

        return try {
            val response = profileApi.updateProfile(
                bannerImage = bannerFile?.let {
                    MultipartBody.Part
                        .createFormData(
                            "banner_image",
                            bannerFile.name,
                            bannerFile.asRequestBody()
                        )
                },
                profilePicture = profilePictureFile?.let {
                    MultipartBody.Part
                        .createFormData(
                            "profile_picture",
                            profilePictureFile.name,
                            profilePictureFile.asRequestBody()
                        )
                },
                updateProfileData = MultipartBody.Part
                    .createFormData(
                        "update_profile_data",
                        gson.toJson(updateProfileData)
                    )
            )
            if (response.successful) {
                Resource.Success(uiText = null)
            } else {
                response.message?.let { msg ->
                    Resource.Error(uiText = UiText.CallResponseText(msg))
                } ?: Resource.Error(uiText = UiText.StringResource(R.string.unknown_error))
            }
        } catch (e: IOException) {
            Resource.Error(
                uiText = UiText.StringResource(R.string.fail_to_connect)
            )
        } catch (e: HttpException) {
            Resource.Error(
                uiText = UiText.StringResource(R.string.fail_to_connect)
            )
        }
    }

    override suspend fun getOwnPagedPosts(
        userId: String,
        page: Int,
        pageSize: Int
    ): ProfileOwnPostResource {
        return try {
            val response = postApi.getPostsForProfile(
                userId = userId,
                page = page,
                pageSize = pageSize
            )
            Resource.Success(
                data = response.map { it.toPost() },
                uiText = null
            )
        } catch (e: IOException) {
            Resource.Error(
                uiText = UiText.StringResource(R.string.fail_to_connect)
            )
        } catch (e: HttpException) {
            Resource.Error(
                uiText = UiText.StringResource(R.string.fail_to_connect)
            )
        }
    }

    override suspend fun getLikedPosts(
        userId: String,
        page: Int,
        pageSize: Int
    ): ProfileLikedPostResource {
        return try {
            val response = postApi.getPostsForLike(
                userId = userId,
                page = page,
                pageSize = pageSize
            )
            Resource.Success(
                data = response.map { it.toPost() },
                uiText = null
            )
        } catch (e: IOException) {
            Resource.Error(
                uiText = UiText.StringResource(R.string.fail_to_connect)
            )
        } catch (e: HttpException) {
            Resource.Error(
                uiText = UiText.StringResource(R.string.fail_to_connect)
            )
        }
    }

    override suspend fun searchUser(query: String): SearchResource {
        return try {
            val response = profileApi.searchUser(query)
            Resource.Success(
                data = response.map {
                    it.toUserItem()
                },
                uiText = null
            )
        } catch (e: IOException) {
            Resource.Error(
                uiText = UiText.StringResource(R.string.fail_to_connect)
            )
        } catch (e: HttpException) {
            Resource.Error(
                uiText = UiText.StringResource(R.string.fail_to_connect)
            )
        }
    }

    override suspend fun followUser(userId: String): FollowUpdateResource {
        return try {
            val response = profileApi.followUser(FollowUpdateRequest(userId))
            if (response.successful) {
                Resource.Success(uiText = null)
            } else {
                response.message?.let {
                    Resource.Error(uiText = UiText.CallResponseText(value = it))
                } ?: Resource.Error(uiText = UiText.StringResource(R.string.unknown_error))
            }
        } catch (e: IOException) {
            Resource.Error(
                uiText = UiText.StringResource(R.string.fail_to_connect)
            )
        } catch (e: HttpException) {
            Resource.Error(
                uiText = UiText.StringResource(R.string.fail_to_connect)
            )
        }
    }

    override suspend fun unfollowUser(userId: String): FollowUpdateResource {
        return try {
            val response = profileApi.unfollowUser(FollowUpdateRequest(userId))
            if (response.successful) {
                Resource.Success(uiText = null)
            } else {
                response.message?.let { msg ->
                    Resource.Error(uiText = UiText.CallResponseText(msg))
                } ?: Resource.Error(uiText = UiText.StringResource(R.string.unknown_error))
            }
        } catch (e: IOException) {
            Resource.Error(
                uiText = UiText.StringResource(R.string.fail_to_connect)
            )
        } catch (e: HttpException) {
            Resource.Error(
                uiText = UiText.StringResource(R.string.fail_to_connect)
            )
        }
    }

    override suspend fun getComments(
        page: Int,
        pageSize: Int
    ): CommentsForUserResource {
        return try {
            val response = profileApi.getComments(
                page = page,
                pageSize = pageSize
            )
            if (response.successful) {
                Resource.Success(
                    data = response.data?.map { it.toComment() } ?: emptyList(),
                    uiText = null
                )
            } else {
                response.message?.let { msg ->
                    Resource.Error(uiText = UiText.CallResponseText(msg))
                } ?: Resource.Error(uiText = UiText.StringResource(R.string.unknown_error))
            }
        } catch (e: IOException) {
            Resource.Error(
                uiText = UiText.StringResource(R.string.fail_to_connect)
            )
        } catch (e: HttpException) {
            Resource.Error(
                uiText = UiText.StringResource(R.string.fail_to_connect)
            )
        }
    }
}
