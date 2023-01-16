package com.code.block.feature.profile.presentation.profilescreen

import androidx.compose.foundation.layout.* // ktlint-disable no-wildcard-imports
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import com.code.block.R
import com.code.block.core.domain.model.Post
import com.code.block.core.domain.model.User
import com.code.block.core.presentation.components.StandardTopBar
import com.code.block.core.presentation.ui.theme.ProfilePictureSizeLarge
import com.code.block.core.presentation.ui.theme.SpaceSmall
import com.code.block.feature.destinations.EditProfileScreenDestination
import com.code.block.feature.profile.presentation.profilescreen.components.BannerSection
import com.code.block.feature.profile.presentation.profilescreen.components.ProfileHeaderSection
import com.code.block.feature.profile.presentation.profilescreen.components.tablayout.Tabs
import com.code.block.feature.profile.presentation.profilescreen.components.tablayout.TabsContent
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.rememberPagerState
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@OptIn(ExperimentalPagerApi::class)
@Destination
@Composable
fun ProfileScreen(
    user: User = User(
        profilePictureUrl = R.drawable.batman_profile_image,
        username = "Batman",
        description = R.string.test_description,
        followerCount = 10,
        followingCount = 10,
        postCount = 10
    ),
    navigator: DestinationsNavigator
) {
    val pagerState = rememberPagerState(
        initialPage = 0,
        pageCount = 3
    )

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

            BannerSection(user = user)

            Spacer(modifier = Modifier.height(ProfilePictureSizeLarge / 2f - SpaceSmall))

            Column(
                modifier = Modifier.fillMaxSize()
            ) {
                ProfileHeaderSection(
                    user = user,
                    isOwnProfile = true,
                    modifier = Modifier,
                    onEditClick = {
                        navigator.navigate(EditProfileScreenDestination)
                    }
                )

                Spacer(modifier = Modifier.height(SpaceSmall))

                Column(
                    modifier = Modifier
                        .height(screenHeight)
                ) {
                    Tabs(pagerState = pagerState)

                    TabsContent(
                        scrollState = scrollState,
                        pagerState = pagerState,
                        post = Post(
                            username = "Batman",
                            contentUrl = "",
                            profilePictureUrl = "",
                            description = stringResource(id = R.string.test_string),
                            likeCount = 17,
                            commentCount = 7,
                            timestamp = 0
                        ),
                        navigator = navigator
                    )
                }
            }
        }
    }
}
