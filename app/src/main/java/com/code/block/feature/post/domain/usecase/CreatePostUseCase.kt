package com.code.block.feature.post.domain.usecase

import android.net.Uri
import com.code.block.R
import com.code.block.core.utils.CreatePostResource
import com.code.block.core.utils.Resource
import com.code.block.core.utils.UiText
import com.code.block.feature.post.domain.repository.PostRepository

class CreatePostUseCase(
    private val repository: PostRepository
) {
    suspend operator fun invoke(
        description: String,
        contentUri: Uri?
    ): CreatePostResource {
        if (contentUri == null) {
            return Resource.Error(
                uiText = UiText.StringResource(R.string.no_content_picked)
            )
        }
        if (description.isBlank()) {
            return Resource.Error(
                uiText = UiText.StringResource(R.string.no_des_input)
            )
        }

        return repository.createPost(
            description = description,
            contentUri = contentUri
        )
    }
}
