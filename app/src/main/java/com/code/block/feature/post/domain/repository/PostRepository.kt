package com.code.block.feature.post.domain.repository

import android.net.Uri
import androidx.paging.PagingData
import com.code.block.core.domain.model.Post
import com.code.block.core.domain.util.CommentsForPostResource
import com.code.block.core.domain.util.CreateCommentResource
import com.code.block.core.domain.util.CreatePostResource
import com.code.block.core.domain.util.PostDetailResource
import kotlinx.coroutines.flow.Flow

interface PostRepository {
    val posts: Flow<PagingData<Post>>

    suspend fun createPost(
        description: String,
        contentUri: Uri
    ): CreatePostResource

    suspend fun getPostDetail(postId: String): PostDetailResource

    suspend fun getCommentsForPost(postId: String): CommentsForPostResource

    suspend fun createComment(
        comment: String,
        postId: String
    ): CreateCommentResource
}
