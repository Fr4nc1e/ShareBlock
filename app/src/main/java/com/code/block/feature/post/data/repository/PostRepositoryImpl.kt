package com.code.block.feature.post.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.code.block.core.domain.model.Post
import com.code.block.core.utils.Constants
import com.code.block.feature.post.data.source.paging.PostSource
import com.code.block.feature.post.data.source.remote.PostApi
import com.code.block.feature.post.domain.repository.PostRepository
import kotlinx.coroutines.flow.Flow

class PostRepositoryImpl(
    private val api: PostApi
) : PostRepository {

    override val posts: Flow<PagingData<Post>>
        get() = Pager(
            config = PagingConfig(
                pageSize = Constants.PAGE_SIZE_POSTS
            ),
            pagingSourceFactory = {
                PostSource(api)
            }
        ).flow
}
