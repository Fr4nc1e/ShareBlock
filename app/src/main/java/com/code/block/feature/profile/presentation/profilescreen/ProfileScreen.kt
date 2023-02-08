package com.code.block.feature.profile.presentation.profilescreen

import android.util.Base64
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.* // ktlint-disable no-wildcard-imports
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.* // ktlint-disable no-wildcard-imports
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.* // ktlint-disable no-wildcard-imports
import androidx.compose.runtime.* // ktlint-disable no-wildcard-imports
import androidx.compose.ui.Alignment.Companion.End
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import com.code.block.R
import com.code.block.core.domain.model.User
import com.code.block.core.presentation.components.Screen
import com.code.block.core.presentation.components.StandardTopBar
import com.code.block.core.presentation.ui.theme.SpaceMedium
import com.code.block.core.util.ui.UiEvent
import com.code.block.core.util.ui.asString
import com.code.block.feature.profile.presentation.profilescreen.components.ProfileHeadSection
import com.code.block.feature.profile.presentation.profilescreen.components.tablayout.Tabs
import com.code.block.feature.profile.presentation.profilescreen.components.tablayout.TabsContent
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.flow.collectLatest

@Suppress("OPT_IN_IS_NOT_ENABLED")
@OptIn(ExperimentalPagerApi::class)
@Composable
fun ProfileScreen(
    userId: String? = null,
    onNavigate: (String) -> Unit = {},
    scaffoldState: ScaffoldState,
    onLogout: () -> Unit = {},
    viewModel: ProfileViewModel = hiltViewModel(),
) {
    val ownPagingState = viewModel.ownPagingState.value
    val likePagingState = viewModel.likePagingState.value
    val commentPagingState = viewModel.commentPagingState.value
    val pagerState = rememberPagerState(
        initialPage = 0,
        pageCount = 3,
    )
    val state = viewModel.state.value
    val context = LocalContext.current

    LaunchedEffect(key1 = true) {
        viewModel.getProfile(userId)
        viewModel.eventFlow.collectLatest {
            when (it) {
                is UiEvent.SnackBarEvent -> {
                    scaffoldState.snackbarHostState.showSnackbar(
                        message = it.uiText.asString(context),
                    )
                }
                is UiEvent.Navigate -> {
                    onNavigate(it.route)
                }
                else -> Unit
            }
        }
    }

    BoxWithConstraints {
        val screenHeight = maxHeight
        val scrollState = rememberScrollState()

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(state = scrollState),
        ) {
            StandardTopBar(
                title = {
                    Text(
                        text = stringResource(R.string.profile),
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colors.onBackground,
                    )
                },
                navActions = {
                    state.profile?.let {
                        if (it.isOwnProfile) {
                            IconButton(onClick = { viewModel.onEvent(ProfileEvent.ShowMenu) }) {
                                Icon(imageVector = Icons.Default.MoreVert, contentDescription = null)
                            }
                            MaterialTheme(
                                shapes = MaterialTheme.shapes.copy(medium = RoundedCornerShape(16.dp)),
                            ) {
                                DropdownMenu(
                                    expanded = state.showMenu,
                                    onDismissRequest = { viewModel.onEvent(ProfileEvent.ShowMenu) },
                                ) {
                                    DropdownMenuItem(
                                        onClick = {
                                            onNavigate(Screen.EditProfileScreen.route + "/${it.userId}")
                                        },
                                    ) {
                                        Icon(
                                            imageVector = Icons.Default.Edit,
                                            contentDescription = null,
                                        )
                                        Text(
                                            text = "Edit profile",
                                            color = MaterialTheme.colors.onSurface,
                                        )
                                    }
                                    DropdownMenuItem(
                                        onClick = { viewModel.onEvent(ProfileEvent.ShowLogoutDialog) },
                                    ) {
                                        Icon(
                                            imageVector = Icons.Default.Logout,
                                            contentDescription = null,
                                        )
                                        Text(
                                            text = "Log out",
                                            color = MaterialTheme.colors.onSurface,
                                        )
                                    }
                                }
                            }
                        }
                    }
                },
                modifier = Modifier.fillMaxWidth(),
            )

            Column(modifier = Modifier.fillMaxSize()) {
                state.profile?.let { profile ->
                    val encodedProfilePictureUrl = Base64.encodeToString(
                        /* input = */ profile.profilePictureUrl.encodeToByteArray(),
                        /* flags = */ 0,
                    )
                    ProfileHeadSection(
                        profile = profile,
                        user = User(
                            userId = profile.userId,
                            profilePictureUrl = profile.profilePictureUrl,
                            username = profile.username,
                            description = profile.bio,
                            followerCount = profile.followerCount,
                            followingCount = profile.followingCount,
                            postCount = profile.postCount,
                        ),
                        bitmap = state.bitmap,
                        isOwnProfile = profile.isOwnProfile,
                        isFollowing = profile.isFollowing,
                        modifier = Modifier,
                        onFollowClick = {
                            viewModel.onEvent(ProfileEvent.FollowMotion(profile.userId))
                        },
                        onFollowingClick = {
                            viewModel.onEvent(ProfileEvent.Followings(profile.userId))
                        },
                        onFollowerClick = {
                            viewModel.onEvent(ProfileEvent.Followers(profile.userId))
                        },
                        onMessageClick = {
                            onNavigate(
                                Screen.MessageScreen.route +
                                    "/${profile.userId}" +
                                    "/${profile.username}" +
                                    "/$encodedProfilePictureUrl",
                            )
                        },
                    )
                }

                Column(modifier = Modifier.height(screenHeight)) {
                    Tabs(pagerState = pagerState)
                    TabsContent(
                        scrollState = scrollState,
                        pagerState = pagerState,
                        ownPagingState = ownPagingState,
                        likedPagingState = likePagingState,
                        commentPagingState = commentPagingState,
                        onNavigate = onNavigate,
                    )
                }
            }
        }
    }

    if (state.isLogoutDialogVisible) {
        Dialog(
            onDismissRequest = {
                viewModel.onEvent(ProfileEvent.DismissLogoutDialog)
            },
        ) {
            Card(
                modifier = Modifier
                    .background(
                        color = MaterialTheme.colors.surface,
                        shape = MaterialTheme.shapes.medium,
                    )
                    .padding(SpaceMedium)
                    .clip(RoundedCornerShape(16.dp)),
            ) {
            }
            Column(
                modifier = Modifier.padding(SpaceMedium),
            ) {
                Text(
                    text = stringResource(id = R.string.logout),
                    color = MaterialTheme.colors.onBackground,
                )
                Spacer(modifier = Modifier.height(SpaceMedium))
                Row(
                    horizontalArrangement = Arrangement.End,
                    modifier = Modifier.align(End),
                ) {
                    Text(
                        text = stringResource(id = R.string.no).uppercase(),
                        color = MaterialTheme.colors.onBackground,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.clickable {
                            viewModel.onEvent(ProfileEvent.DismissLogoutDialog)
                        },
                    )
                    Spacer(modifier = Modifier.width(SpaceMedium))
                    Text(
                        text = stringResource(id = R.string.yes).uppercase(),
                        color = MaterialTheme.colors.primary,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.clickable {
                            viewModel.onEvent(ProfileEvent.Logout)
                            viewModel.onEvent(ProfileEvent.DismissLogoutDialog)
                            onLogout()
                        },
                    )
                }
            }
        }
    }
}
