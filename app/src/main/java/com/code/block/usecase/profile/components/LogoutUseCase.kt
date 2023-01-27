package com.code.block.usecase.profile.components

import com.code.block.feature.profile.domain.repository.ProfileRepository

class LogoutUseCase(
    private val repository: ProfileRepository
) {
    operator fun invoke() {
        repository.logout()
    }
}
