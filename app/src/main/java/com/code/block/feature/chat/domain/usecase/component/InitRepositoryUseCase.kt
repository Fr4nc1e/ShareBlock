package com.code.block.feature.chat.domain.usecase.component

import com.code.block.feature.chat.domain.repository.ChatRepository

class InitRepositoryUseCase(
    private val repository: ChatRepository
) {

    operator fun invoke() {
        repository.initialize()
    }
}
