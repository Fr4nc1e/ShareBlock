package com.code.block.feature.profile.presentation.profilescreen

import com.code.block.core.domain.model.Profile

data class ProfileState(
    val profile: Profile? = null,
    val isLoading: Boolean = false
)
