package com.code.block.core.domain.util

import com.code.block.core.domain.model.Comment
import com.code.block.core.domain.model.Post
import com.code.block.core.domain.model.Profile
import com.code.block.core.util.UiText
import com.code.block.feature.profile.domain.model.UserItem

sealed class Resource<T>(
    val data: T? = null,
    val uiText: UiText? = null
) {
    class Success<T>(data: T? = null, uiText: UiText?) : Resource<T>(data, uiText)
    class Error<T>(data: T? = null, uiText: UiText?) : Resource<T>(data, uiText)
}

typealias RegisterResource = Resource<Unit>
typealias LoginResource = Resource<Unit>
typealias AuthenticationResource = Resource<Unit>
typealias CreatePostResource = Resource<Unit>
typealias ProfileResource = Resource<Profile>
typealias UpdateProfileResource = Resource<Unit>
typealias SearchResource = Resource<List<UserItem>>
typealias FollowUpdateResource = Resource<Unit>
typealias PostDetailResource = Resource<Post>
typealias CommentsForPostResource = Resource<List<Comment>>
typealias CreateCommentResource = Resource<Unit>
