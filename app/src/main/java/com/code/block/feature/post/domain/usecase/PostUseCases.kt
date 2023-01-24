package com.code.block.feature.post.domain.usecase

data class PostUseCases(
    val getPostsForFollowUseCase: GetPostsForFollowUseCase,
    val createPostUseCase: CreatePostUseCase,
    val getCommentsForPostUseCase: GetCommentsForPostUseCase,
    val getPostDetailUseCase: GetPostDetailUseCase,
    val createCommentUseCase: CreateCommentUseCase,
    val likeParentUseCase: LikeParentUseCase,
    val getLikedUsersForParent: GetLikedUsersForParent
)
