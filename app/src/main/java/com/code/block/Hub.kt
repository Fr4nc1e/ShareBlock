package com.code.block

import android.content.Intent
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.navigation.navDeepLink
import com.code.block.core.presentation.components.Screen
import com.code.block.core.presentation.components.StandardScaffold
import com.code.block.feature.activity.presentation.activityscreen.ActivityScreen
import com.code.block.feature.auth.presentation.loginscreen.LoginScreen
import com.code.block.feature.auth.presentation.registerscreen.RegisterScreen
import com.code.block.feature.auth.presentation.splashscreen.SplashScreen
import com.code.block.feature.chat.presentation.chatscreen.ChatScreen
import com.code.block.feature.chat.presentation.messagescreen.MessageScreen
import com.code.block.feature.post.presentation.createpostscreen.CreatePostScreen
import com.code.block.feature.post.presentation.homescreen.HomeScreen
import com.code.block.feature.post.presentation.personlistscreen.PersonListScreen
import com.code.block.feature.post.presentation.postdetailscreen.PostDetailScreen
import com.code.block.feature.profile.presentation.editprofilescreen.EditProfileScreen
import com.code.block.feature.profile.presentation.followinfoscreen.FollowInfoScreen
import com.code.block.feature.profile.presentation.profilescreen.ProfileScreen
import com.code.block.feature.profile.presentation.searchscreen.SearchScreen

@Composable
fun Hub(
    modifier: Modifier
) {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val scaffoldState = rememberScaffoldState()

    StandardScaffold(
        navController = navController,
        showBottomBar = shouldShowBottomBar(navBackStackEntry),
        state = scaffoldState,
        modifier = modifier,
        onFabClick = {
            navController.navigate(Screen.CreatePostScreen.route)
        }
    ) {
        NavHost(
            navController = navController,
            startDestination = Screen.SplashScreen.route
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
                    onRegister = {
                        navController.popBackStack(
                            route = Screen.RegisterScreen.route,
                            inclusive = true
                        )
                        navController.navigate(route = Screen.LoginScreen.route)
                    },
                    scaffoldState = scaffoldState
                )
            }

            composable(Screen.HomeScreen.route) {
                HomeScreen(
                    onNavigate = navController::navigate,
                    scaffoldState = scaffoldState
                )
            }

            composable(Screen.ChatScreen.route) {
                ChatScreen(
                    onNavigate = navController::navigate
                )
            }

            composable(
                route = Screen.MessageScreen.route +
                    "/{remoteUserId}" +
                    "/{remoteUsername}" +
                    "/{remoteUserProfilePictureUrl}" +
                    "?chatId={chatId}",
                arguments = listOf(
                    navArgument("chatId") {
                        type = NavType.StringType
                        nullable = true
                    },
                    navArgument("remoteUserId") {
                        type = NavType.StringType
                    },
                    navArgument("remoteUsername") {
                        type = NavType.StringType
                    },
                    navArgument("remoteUserProfilePictureUrl") {
                        type = NavType.StringType
                    }
                )
            ) { navBackStackEntry ->
                val remoteUserId = navBackStackEntry
                    .arguments?.getString("remoteUserId")!!
                val remoteUsername = navBackStackEntry
                    .arguments?.getString("remoteUsername")!!
                val remoteUserProfilePictureUrl = navBackStackEntry
                    .arguments?.getString("remoteUserProfilePictureUrl")!!
                MessageScreen(
                    remoteUserId = remoteUserId,
                    remoteUsername = remoteUsername,
                    encodedRemoteUserProfilePictureUrl = remoteUserProfilePictureUrl,
                    onNavigate = navController::navigate
                )
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
                    onLogout = {
                        navController.popBackStack()
                        navController.navigate(route = Screen.LoginScreen.route)
                    },
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
                route = Screen.PostDetailScreen.route + "/{postId}" +
                    "?shouldShowKeyboard={shouldShowKeyboard}",
                arguments = listOf(
                    navArgument(
                        name = "postId"
                    ) {
                        type = NavType.StringType
                    },
                    navArgument(
                        name = "shouldShowKeyboard"
                    ) {
                        type = NavType.BoolType
                        defaultValue = false
                    }
                ),
                deepLinks = listOf(
                    navDeepLink {
                        action = Intent.ACTION_VIEW
                        uriPattern = "myapp://block.com/{postId}"
                    }
                )
            ) { it1 ->
                val shouldShowKeyboard = it1.arguments?.getBoolean("showShowKeyboard") ?: false
                PostDetailScreen(
                    onNavigate = navController::navigate,
                    scaffoldState = scaffoldState,
                    shouldShowKeyboard = shouldShowKeyboard
                )
            }

            composable(
                route = Screen.PersonListScreen.route + "/{parentId}",
                arguments = listOf(
                    navArgument("parentId") {
                        type = NavType.StringType
                    }
                )
            ) {
                PersonListScreen(
                    onNavigate = navController::navigate,
                    scaffoldState = scaffoldState
                )
            }

            composable(
                route = Screen.FollowInfoScreen.route + "/{followType}/{userId}",
                arguments = listOf(
                    navArgument("userId") {
                        type = NavType.StringType
                    },
                    navArgument("followType") {
                        type = NavType.StringType
                    }
                )
            ) {
                FollowInfoScreen(
                    onNavigate = navController::navigate,
                    scaffoldState = scaffoldState
                )
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
    val isOwnProfile = backStackEntry
        ?.destination
        ?.route == "${Screen.ProfileScreen.route}?userId={userId}" &&
        backStackEntry.arguments?.getString("userId") == null
    return doesRouteMatch || isOwnProfile
}
