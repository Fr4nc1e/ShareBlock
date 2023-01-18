package com.code.block.feature.profile.data.repository

import com.code.block.R
import com.code.block.core.utils.ProfileResource
import com.code.block.core.utils.Resource
import com.code.block.core.utils.UiText
import com.code.block.feature.profile.data.source.ProfileApi
import com.code.block.feature.profile.domain.repository.ProfileRepository
import retrofit2.HttpException
import java.io.IOException

class ProfileRepositoryImpl(
    private val api: ProfileApi
) : ProfileRepository {
    override suspend fun getProfile(userId: String): ProfileResource {
        return try {
            val response = api.getUserProfile(userId)
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
}
