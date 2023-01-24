package com.code.block.feature.profile.presentation.profilescreen

import androidx.compose.foundation.layout.* // ktlint-disable no-wildcard-imports
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ScaffoldState
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.hilt.navigation.compose.hiltViewModel
import com.code.block.R
import com.code.block.core.domain.model.User
import com.code.block.core.presentation.components.Screen
import com.code.block.core.presentation.components.StandardTopBar
import com.code.block.core.presentation.ui.theme.ProfilePictureSizeLarge
import com.code.block.core.presentation.ui.theme.SpaceSmall
import com.code.block.core.util.ui.UiEvent
import com.code.block.core.util.ui.asString
import com.code.block.feature.profile.presentation.profilescreen.components.BannerSection
import com.code.block.feature.profile.presentation.profilescreen.components.ProfileHeaderSection
import com.code.block.feature.profile.presentation.profilescreen.components.tablayout.Tabs
import com.code.block.feature.profile.presentation.profilescreen.components.tablayout.TabsContent
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.flow.collectLatest

@OptIn(ExperimentalPagerApi::class)
@Composable
fun ProfileScreen(
    userId: String? = null,
    onNavigate: (String) -> Unit = {},
    scaffoldState: ScaffoldState,
    viewModel: ProfileViewModel = hiltViewModel()
) {
    val ownPagingState = viewModel.ownPagingState.value
    val likePagingState = viewModel.likePagingState.value
    val pagerState = rememberPagerState(
        initialPage = 0,
        pageCount = 3
    )
    val state = viewModel.state.value
    val context = LocalContext.current

    LaunchedEffect(key1 = true) {
        viewModel.getProfile(userId)
        viewModel.eventFlow.collectLatest {
            when (it) {
                is UiEvent.SnackBarEvent -> {
                    scaffoldState.snackbarHostState.showSnackbar(
                        message = it.uiText.asString(context)
                    )
                }
                UiEvent.OnLikeParent -> {
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
                .verticalScroll(state = scrollState)
        ) {
            StandardTopBar(
                title = {
                    Text(
                        text = stringResource(R.string.profile),
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colors.onBackground
                    )
                },
                modifier = Modifier.fillMaxWidth()
            )

            BannerSection(
                bannerUrl = state.profile?.bannerUrl,
                profilePictureUrl = state.profile?.profilePictureUrl
            )

            Spacer(modifier = Modifier.height(ProfilePictureSizeLarge / 2f - SpaceSmall))

            Column(
                modifier = Modifier.fillMaxSize()
            ) {
                state.profile?.let { profile ->
                    ProfileHeaderSection(
                        user = User(
                            userId = profile.userId,
                            profilePictureUrl = profile.profilePictureUrl,
                            username = profile.username,
                            description = profile.bio,
                            followerCount = profile.followerCount,
                            followingCount = profile.followingCount,
                            postCount = profile.postCount
                        ),
                        isOwnProfile = profile.isOwnProfile,
                        isFollowing = profile.isFollowing,
                        modifier = Modifier,
                        onFollowClick = {},
                        onFollowingClick = {},
                        onFollowerClick = {},
                        onEditClick = {
                            onNavigate(Screen.EditProfileScreen.route + "/${profile.userId}")
                        }
                    )
                }

                Column(
                    modifier = Modifier
                        .height(screenHeight)
                ) {
                    Tabs(pagerState = pagerState)

                    TabsContent(
                        scrollState = scrollState,
                        pagerState = pagerState,
                        ownPagingState = ownPagingState,
                        likedPagingState = likePagingState,
                        onNavigate = onNavigate
                    )
                }
            }
        }
    }
}
