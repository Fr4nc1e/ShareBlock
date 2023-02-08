package com.code.block.feature.profile.presentation.profilescreen

import android.graphics.Bitmap
import com.code.block.core.domain.model.Profile

data class ProfileState(
    val profile: Profile? = null,
    val bitmap: Bitmap? = null,
    val isLoading: Boolean = false,
    val showMenu: Boolean = false,
    val isLogoutDialogVisible: Boolean = false,
)
