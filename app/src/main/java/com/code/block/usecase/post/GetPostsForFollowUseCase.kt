package com.code.block.usecase.post

import com.code.block.core.domain.resource.HomePostsResource
import com.code.block.core.util.Constants
import com.code.block.feature.post.domain.repository.PostRepository

class GetPostsForFollowUseCase(
    private val repository: PostRepository
) {
    suspend operator fun invoke(
        page: Int,
        pageSize: Int = Constants.PAGE_SIZE_POSTS
    ): HomePostsResource {
        return repository.getHomePosts(
            page = page,
            pageSize = pageSize
        )
    }
}
