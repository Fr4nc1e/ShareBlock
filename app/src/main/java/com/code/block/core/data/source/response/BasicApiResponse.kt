package com.code.block.core.data.source.response

import com.code.block.feature.auth.data.source.remote.response.AuthResponse
import com.code.block.feature.post.data.source.response.CommentDto
import com.code.block.feature.post.data.source.response.PostDto
import com.code.block.feature.profile.data.source.response.UserItemDto

data class BasicApiResponse<T>(
    val successful: Boolean,
    val message: String? = null,
    val data: T? = null
)

typealias RegisterResponse = BasicApiResponse<Unit>
typealias LoginResponse = BasicApiResponse<AuthResponse>
typealias CreatePostResponse = BasicApiResponse<Unit>
typealias UpdateProfileResponse = BasicApiResponse<Unit>
typealias FollowUpdateResponse = BasicApiResponse<Unit>
typealias PostDetailResponse = BasicApiResponse<PostDto>
typealias CommentsForPostResponse = BasicApiResponse<List<CommentDto>>
typealias CommentsForUserResponse = BasicApiResponse<List<CommentDto>>
typealias CreateCommentResponse = BasicApiResponse<Unit>
typealias LikeUpdateResponse = BasicApiResponse<Unit>
typealias LikedUsersResponse = BasicApiResponse<List<UserItemDto>>
