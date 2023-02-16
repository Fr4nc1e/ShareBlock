package com.code.block.core.data.source.response

import com.code.block.feature.auth.data.source.remote.response.AuthResponse
import com.code.block.feature.post.data.source.response.CommentResponse
import com.code.block.feature.post.data.source.response.PostResponse
import com.code.block.feature.profile.data.source.response.UserItemResponse

data class BasicApiResponse<T>(
    val successful: Boolean,
    val message: String? = null,
    val data: T? = null,
)

typealias RegisterResponse = BasicApiResponse<Unit>
typealias LoginResponse = BasicApiResponse<AuthResponse>
typealias CreatePostResponse = BasicApiResponse<Unit>
typealias UpdateProfileResponse = BasicApiResponse<Unit>
typealias FollowUpdateResponse = BasicApiResponse<Unit>
typealias PostDetailResponse = BasicApiResponse<PostResponse>
typealias CommentsForPostResponse = BasicApiResponse<List<CommentResponse>>
typealias CommentsForUserResponse = BasicApiResponse<List<CommentResponse>>
typealias CreateCommentResponse = BasicApiResponse<Unit>
typealias LikeUpdateResponse = BasicApiResponse<Unit>
typealias LikedUsersResponse = BasicApiResponse<List<UserItemResponse>>
typealias FollowingUsersResponse = BasicApiResponse<List<UserItemResponse>>
typealias FollowedUsersResponse = BasicApiResponse<List<UserItemResponse>>
typealias ChannelIdResponse = BasicApiResponse<String>
typealias SendPostNotificationResponse = BasicApiResponse<Unit>
