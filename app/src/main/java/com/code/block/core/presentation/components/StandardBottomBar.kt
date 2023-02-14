package com.code.block.core.presentation.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.BottomAppBar
import androidx.compose.material.BottomNavigation
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.code.block.core.domain.model.BottomNavItem

@Composable
fun StandardBottomBar(
    navController: NavController,
    showBottomBar: Boolean = true,
) {
    if (showBottomBar) {
        BottomAppBar(
            modifier = Modifier
                .fillMaxWidth()
                .clip(
                    RoundedCornerShape(
                        200.dp,
                        200.dp,
                        0.dp,
                        0.dp
                    )
                ),
            backgroundColor = MaterialTheme.colors.surface,
            cutoutShape = CircleShape,
            elevation = 5.dp,
        ) {
            BottomNavigation {
                BottomNavItem.values().forEachIndexed { _, bottomNavItem ->
                    StandardBottomNavItem(
                        icon = bottomNavItem.icon,
                        contentDescription = stringResource(id = bottomNavItem.contentDescription),
                        selected = navController.currentDestination?.route?.startsWith(bottomNavItem.route) == true,
                        alertCount = bottomNavItem.alertCount,
                        enabled = bottomNavItem.icon != null,
                    ) {
                        if (navController.currentDestination?.route != bottomNavItem.route) {
                            navController.popBackStack()
                            navController.navigate(bottomNavItem.route)
                        }
                    }
                }
            }
        }
    }
}
