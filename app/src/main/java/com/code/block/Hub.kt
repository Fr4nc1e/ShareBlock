package com.code.block

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.paging.compose.collectAsLazyPagingItems
import com.code.block.core.presentation.components.Screen
import com.code.block.core.presentation.components.StandardScaffold
import com.code.block.feature.activity.presentation.activityscreen.ActivityScreen
import com.code.block.feature.auth.presentation.loginscreen.LoginScreen
import com.code.block.feature.auth.presentation.registerscreen.RegisterScreen
import com.code.block.feature.auth.presentation.splashscreen.SplashScreen
import com.code.block.feature.chat.presentation.chatscreen.ChatScreen
import com.code.block.feature.post.presentation.createpostscreen.CreatePostScreen
import com.code.block.feature.post.presentation.homescreen.HomeScreen
import com.code.block.feature.post.presentation.homescreen.HomeViewModel
import com.code.block.feature.post.presentation.personlistscreen.PersonListScreen
import com.code.block.feature.post.presentation.postdetailscreen.PostDetailScreen
import com.code.block.feature.profile.presentation.editprofilescreen.EditProfileScreen
import com.code.block.feature.profile.presentation.profilescreen.ProfileScreen
import com.code.block.feature.profile.presentation.searchscreen.SearchScreen

@Composable
fun Hub() {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val scaffoldState = rememberScaffoldState()
    val lazyListState = rememberLazyListState()
    val homeViewModel = hiltViewModel<HomeViewModel>()
    val posts = homeViewModel.posts.collectAsLazyPagingItems()

    StandardScaffold(
        navController = navController,
        showBottomBar = shouldShowBottomBar(navBackStackEntry),
        state = scaffoldState,
        modifier = Modifier.fillMaxSize(),
        onFabClick = {
            navController.navigate(Screen.CreatePostScreen.route)
        }
    ) {
        NavHost(
            navController = navController,
            startDestination = Screen.SplashScreen.route,
            modifier = Modifier.padding(it)
        ) {
            composable(Screen.SplashScreen.route) {
                SplashScreen(
                    onPopBackStack = navController::popBackStack,
                    onNavigate = navController::navigate
                )
            }

            composable(Screen.LoginScreen.route) {
                LoginScreen(
                    onNavigate = navController::navigate,
                    onLogin = {
                        navController.popBackStack(
                            route = Screen.LoginScreen.route,
                            inclusive = true
                        )
                        navController.navigate(route = Screen.HomeScreen.route)
                    },
                    scaffoldState = scaffoldState
                )
            }

            composable(Screen.RegisterScreen.route) {
                RegisterScreen(
                    navController = navController,
                    scaffoldState = scaffoldState
                )
            }

            composable(Screen.HomeScreen.route) {
                HomeScreen(
                    onNavigate = navController::navigate,
                    scaffoldState = scaffoldState,
                    lazyListState = lazyListState,
                    posts = posts
                )
            }

            composable(Screen.ChatScreen.route) {
                ChatScreen()
            }

            composable(Screen.ActivityScreen.route) {
                ActivityScreen(
                    onNavigate = navController::navigate
                )
            }

            composable(
                route = Screen.ProfileScreen.route + "?userId={userId}",
                arguments = listOf(
                    navArgument(name = "userId") {
                        type = NavType.StringType
                        nullable = true
                        defaultValue = null
                    }
                )
            ) { backStackEntry ->
                ProfileScreen(
                    userId = backStackEntry.arguments?.getString("userId"),
                    onNavigate = navController::navigate,
                    scaffoldState = scaffoldState
                )
            }

            composable(
                Screen.EditProfileScreen.route + "/{userId}",
                arguments = listOf(
                    navArgument(name = "userId") {
                        type = NavType.StringType
                    }
                )
            ) {
                EditProfileScreen(
                    onNavigateUp = navController::navigateUp,
                    onNavigate = navController::navigate,
                    scaffoldState = scaffoldState
                )
            }

            composable(Screen.CreatePostScreen.route) {
                CreatePostScreen(
                    onNavigateUp = navController::navigateUp,
                    scaffoldState = scaffoldState
                )
            }

            composable(Screen.SearchScreen.route) {
                SearchScreen(onNavigate = navController::navigate)
            }

            composable(
                route = Screen.PostDetailScreen.route + "/{postId}",
                arguments = listOf(
                    navArgument(
                        name = "postId"
                    ) {
                        type = NavType.StringType
                    }
                )
            ) {
                PostDetailScreen(
                    onNavigate = navController::navigate,
                    scaffoldState = scaffoldState
                )
            }

            composable(Screen.PersonListScreen.route) {
                PersonListScreen()
            }
        }
    }
}

fun shouldShowBottomBar(backStackEntry: NavBackStackEntry?): Boolean {
    val doesRouteMatch = backStackEntry?.destination?.route in listOf(
        Screen.HomeScreen.route,
        Screen.ChatScreen.route,
        Screen.ActivityScreen.route
    )
    val isOwnProfile = backStackEntry?.destination?.route == "${Screen.ProfileScreen.route}?userId={userId}" &&
        backStackEntry.arguments?.getString("userId") == null
    return doesRouteMatch || isOwnProfile
}
