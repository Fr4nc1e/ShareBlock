package com.code.block.feature.profile.domain.usecase

import androidx.paging.PagingData
import com.code.block.core.domain.model.Post
import com.code.block.feature.profile.domain.repository.ProfileRepository
import kotlinx.coroutines.flow.Flow

class GetOwnPostsProfileUseCase(
    private val repository: ProfileRepository
) {
    operator fun invoke(userId: String): Flow<PagingData<Post>> {
        return repository.getOwnPagedPosts(userId)
    }
}
