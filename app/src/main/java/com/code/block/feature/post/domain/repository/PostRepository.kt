package com.code.block.feature.post.domain.repository

import android.net.Uri
import androidx.paging.PagingData
import com.code.block.core.domain.model.Post
import com.code.block.core.domain.util.* // ktlint-disable no-wildcard-imports
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

    suspend fun likeParent(
        parentId: String,
        parentType: Int
    ): LikeUpdateResource

    suspend fun unlikeParent(
        parentId: String,
        parentType: Int
    ): LikeUpdateResource

    suspend fun getLikedUsers(
        parentId: String
    ): LikedUsersResource
}
