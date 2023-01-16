package com.code.block.feature.post.domain.usecase

import android.net.Uri
import com.code.block.core.utils.CreatePostResource
import com.code.block.feature.post.domain.repository.PostRepository

class CreatePostUseCase(
    private val repository: PostRepository
) {
    suspend operator fun invoke(
        description: String,
        contentUri: Uri
    ): CreatePostResource {
        return repository.createPost(
            description = description,
            contentUri = contentUri
        )
    }
}
