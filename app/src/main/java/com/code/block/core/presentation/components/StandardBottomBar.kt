package com.code.block.core.presentation.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.BottomAppBar
import androidx.compose.material.BottomNavigation
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.code.block.core.domain.model.BottomNavItem
import com.code.block.presentation.NavGraphs
import com.ramcosta.composedestinations.navigation.navigate
import com.ramcosta.composedestinations.navigation.popBackStack
import com.ramcosta.composedestinations.navigation.popUpTo
import com.ramcosta.composedestinations.utils.isRouteOnBackStack

@Composable
fun StandardBottomBar(
    navController: NavHostController,
    showBottomBar: Boolean = true
) {
    if (showBottomBar) {
        BottomAppBar(
            modifier = Modifier.fillMaxWidth(),
            backgroundColor = MaterialTheme.colors.surface,
            cutoutShape = CircleShape,
            elevation = 5.dp
        ) {
            BottomNavigation {
                BottomNavItem.values().forEach {
                    val isCurrentDestinationOnBackStack = navController
                        .isRouteOnBackStack(route = it.route)
                    StandardBottomNavItem(
                        icon = it.icon,
                        contentDescription = stringResource(id = it.contentDescription),
                        selected = isCurrentDestinationOnBackStack,
                        alertCount = it.alertCount,
                        enabled = it.icon != null,
                        onClick = {
                            if (isCurrentDestinationOnBackStack) {
                                navController.popBackStack(it.route, false)
                                return@StandardBottomNavItem
                            }

                            navController.navigate(it.route) {
                                popUpTo(NavGraphs.root) {
                                    saveState = true
                                }

                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                    )
                }
            }
        }
    }
}
