package com.code.block.core.domain.model

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.* // ktlint-disable no-wildcard-imports
import androidx.compose.ui.graphics.vector.ImageVector
import com.code.block.R
import com.code.block.core.presentation.components.Screen

enum class BottomNavItem(
    val route: String,
    val icon: ImageVector? = null,
    @StringRes val contentDescription: Int,
    val alertCount: Int? = null
) {
    Home(
        route = Screen.HomeScreen.route,
        icon = Icons.Outlined.Home,
        contentDescription = R.string.home
    ),
    Chat(
        route = Screen.ChatScreen.route,
        icon = Icons.Outlined.Chat,
        contentDescription = R.string.message,
        alertCount = 100
    ),
    Empty(
        route = Screen.SplashScreen.route,
        contentDescription = R.string.empty_nav_item
    ),
    Activity(
        route = Screen.ActivityScreen.route,
        icon = Icons.Outlined.Notifications,
        contentDescription = R.string.activity,
        alertCount = 100
    ),
    Profile(
        route = Screen.ProfileScreen.route,
        icon = Icons.Outlined.Person,
        contentDescription = R.string.profile
    )
}
