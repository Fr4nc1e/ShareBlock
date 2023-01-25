package com.code.block.core.util.ui.liker

import com.code.block.core.domain.model.Post
import com.code.block.core.domain.resource.Resource

interface Liker {
    suspend fun clickLike(
        posts: List<Post>,
        parentId: String,
        onRequest: suspend (isLiked: Boolean) -> Resource<Unit>,
        onStateUpdate: (List<Post>) -> Unit
    )
}
