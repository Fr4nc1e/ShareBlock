package com.code.block.presentation.profilescreen

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
import com.code.block.domain.model.Post
import com.code.block.domain.model.User
import com.code.block.domain.util.DateFormattedUtil
import com.code.block.presentation.components.StandardTopBar
import com.code.block.presentation.destinations.EditProfileScreenDestination
import com.code.block.presentation.profilescreen.components.BannerSection
import com.code.block.presentation.profilescreen.components.ProfileHeaderSection
import com.code.block.presentation.profilescreen.components.tablayout.Tabs
import com.code.block.presentation.profilescreen.components.tablayout.TabsContent
import com.code.block.ui.theme.ProfilePictureSizeLarge
import com.code.block.ui.theme.SpaceSmall
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
                            imageUrl = R.drawable.hd_batman,
                            profilePictureUrl = R.drawable.batman_profile_image,
                            description = stringResource(id = R.string.test_string),
                            likeCount = 17,
                            commentCount = 7,
                            formattedTime = DateFormattedUtil
                                .timestampToFormattedString(
                                    timestamp = System.currentTimeMillis(),
                                    pattern = "MMM dd, HH:mm"
                                )
                        ),
                        navigator = navigator
                    )
                }
            }
        }
    }
}
