package com.code.block.feature.post.domain.repository

import android.net.Uri
import com.code.block.core.domain.resource.* // ktlint-disable no-wildcard-imports

interface PostRepository {
    suspend fun getHomePosts(
        page: Int,
        pageSize: Int
    ): HomePostsResource

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

    suspend fun deletePost(postId: String): DeleteResource

    suspend fun sendPostNotification(
        title: String,
        description: String
    ): SendPostNotificationResource
}
