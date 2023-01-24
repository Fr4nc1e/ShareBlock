package com.code.block.usecase.profile

import com.code.block.core.domain.resource.Resource
import com.code.block.core.domain.resource.SearchResource
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
