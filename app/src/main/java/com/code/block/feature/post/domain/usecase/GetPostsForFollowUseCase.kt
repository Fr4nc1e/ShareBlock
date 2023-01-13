package com.code.block.feature.post.domain.usecase

import androidx.paging.PagingData
import com.code.block.core.domain.model.Post
import com.code.block.feature.post.domain.repository.PostRepository
import kotlinx.coroutines.flow.Flow

class GetPostsForFollowUseCase(
    private val repository: PostRepository
) {

    operator fun invoke(): Flow<PagingData<Post>> {
        return repository.posts
    }
}
