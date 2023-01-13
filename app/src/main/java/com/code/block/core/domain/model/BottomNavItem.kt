package com.code.block.core.domain.model

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.* // ktlint-disable no-wildcard-imports
import androidx.compose.ui.graphics.vector.ImageVector
import com.code.block.R
import com.code.block.feature.destinations.* // ktlint-disable no-wildcard-imports

enum class BottomNavItem(
    val route: DirectionDestination,
    val icon: ImageVector? = null,
    @StringRes val contentDescription: Int,
    val alertCount: Int? = null
) {
    MainFeed(
        route = HomeScreenDestination,
        icon = Icons.Outlined.Home,
        contentDescription = R.string.home
    ),
    Chat(
        route = ChatScreenDestination,
        icon = Icons.Outlined.Chat,
        contentDescription = R.string.message,
        alertCount = 100
    ),
    Empty(
        route = SplashScreenDestination,
        contentDescription = R.string.empty_nav_item
    ),
    Activity(
        route = ActivityScreenDestination,
        icon = Icons.Outlined.Notifications,
        contentDescription = R.string.activity,
        alertCount = 100
    ),
    Profile(
        route = ProfileScreenDestination,
        icon = Icons.Outlined.Person,
        contentDescription = R.string.profile
    )
}
