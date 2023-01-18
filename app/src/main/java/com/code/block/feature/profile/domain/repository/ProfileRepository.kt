package com.code.block.feature.profile.domain.repository

import com.code.block.core.utils.ProfileResource

interface ProfileRepository {
    suspend fun getProfile(userId: String): ProfileResource
}
