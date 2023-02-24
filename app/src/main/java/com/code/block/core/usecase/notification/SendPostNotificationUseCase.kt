package com.code.block.core.usecase.notification

import com.code.block.core.domain.resource.SendPostNotificationResource
import com.code.block.feature.post.domain.repository.PostRepository

class SendPostNotificationUseCase(
    private val repository: PostRepository
) {
    suspend operator fun invoke(
        title: String,
        description: String
    ): SendPostNotificationResource {
        return repository.sendPostNotification(
            title = title,
            description = description
        )
    }
}
