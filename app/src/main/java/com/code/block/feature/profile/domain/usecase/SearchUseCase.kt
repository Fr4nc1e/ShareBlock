package com.code.block.feature.profile.domain.usecase

import com.code.block.core.domain.util.Resource
import com.code.block.core.domain.util.SearchResource
import com.code.block.feature.profile.domain.repository.ProfileRepository

class SearchUseCase(
    private val profileRepository: ProfileRepository
) {
    suspend operator fun invoke(query: String): SearchResource {
        if (query.isBlank()) {
            return Resource.Success(data = emptyList(), uiText = null)
        }
        return profileRepository.searchUser(query)
    }
}
