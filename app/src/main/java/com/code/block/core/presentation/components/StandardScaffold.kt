package com.code.block.core.presentation.components

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material.* // ktlint-disable no-wildcard-imports
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import com.code.block.R

@Composable
fun StandardScaffold(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    showBottomBar: Boolean = true,
    state: ScaffoldState,
    onFabClick: () -> Unit = {},
    content: @Composable (PaddingValues) -> Unit,
) {
    Scaffold(
        bottomBar = {
            StandardBottomBar(
                navController = navController,
                showBottomBar = showBottomBar,
            )
        },
        floatingActionButton = {
            if (showBottomBar) {
                FloatingActionButton(
                    onClick = onFabClick,
                    backgroundColor = MaterialTheme.colors.primary,
                ) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = stringResource(R.string.make_post),
                    )
                }
            }
        },
        scaffoldState = state,
        isFloatingActionButtonDocked = true,
        floatingActionButtonPosition = FabPosition.Center,
        modifier = modifier,
    ) {
        content(it)
    }
}
