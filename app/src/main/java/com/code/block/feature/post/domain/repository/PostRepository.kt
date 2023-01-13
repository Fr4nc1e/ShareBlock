package com.code.block.feature.post.domain.repository

import androidx.paging.PagingData
import com.code.block.core.domain.model.Post
import kotlinx.coroutines.flow.Flow

interface PostRepository {
    val posts: Flow<PagingData<Post>>
}
