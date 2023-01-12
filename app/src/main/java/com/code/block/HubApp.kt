package com.code.block

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.currentBackStackEntryAsState
import com.code.block.core.presentation.components.StandardScaffold
import com.code.block.feature.NavGraphs
import com.code.block.feature.auth.presentation.registerscreen.RegisterScreen
import com.code.block.feature.destinations.* // ktlint-disable no-wildcard-imports
import com.ramcosta.composedestinations.DestinationsNavHost
import com.ramcosta.composedestinations.manualcomposablecalls.composable
import com.ramcosta.composedestinations.navigation.navigate
import com.ramcosta.composedestinations.rememberNavHostEngine

@Composable
fun HubApp() {
    val engine = rememberNavHostEngine()
    val navController = engine.rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val startRoute = NavGraphs.root.startRoute
    val scaffoldState = rememberScaffoldState()

    StandardScaffold(
        navController = navController,
        showBottomBar = navBackStackEntry?.destination?.route in listOf(
            MainFeedScreenDestination.route,
            ChatScreenDestination.route,
            ActivityScreenDestination.route,
            ProfileScreenDestination.route
        ),
        state = scaffoldState,
        modifier = Modifier.fillMaxSize(),
        onFabClick = {
            navController.navigate(CreatePostScreenDestination)
        }
    ) {
        DestinationsNavHost(
            engine = engine,
            navController = navController,
            navGraph = NavGraphs.root,
            modifier = Modifier.padding(it),
            startRoute = startRoute
        ) {
            composable(RegisterScreenDestination) {
                RegisterScreen(
                    navigator = destinationsNavigator,
                    scaffoldState = scaffoldState
                )
            }
        }
    }
}
