package com.code.block.feature.post.domain.repository

import android.net.Uri
import androidx.paging.PagingData
import com.code.block.core.domain.model.Post
import com.code.block.core.utils.CreatePostResource
import kotlinx.coroutines.flow.Flow

interface PostRepository {
    val posts: Flow<PagingData<Post>>

    suspend fun createPost(
        description: String,
        contentUri: Uri
    ): CreatePostResource
}
