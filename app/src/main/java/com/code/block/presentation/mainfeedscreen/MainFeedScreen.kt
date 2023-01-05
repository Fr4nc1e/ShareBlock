package com.code.block.presentation.mainfeedscreen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import com.code.block.R
import com.code.block.domain.model.Post
import com.code.block.presentation.components.PostCard
import com.code.block.presentation.components.StandardTopBar
import com.code.block.presentation.destinations.PostDetailScreenDestination
import com.code.block.presentation.destinations.SearchScreenDestination
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Destination
@Composable
fun MainFeedScreen(
    navigator: DestinationsNavigator
) {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        StandardTopBar(
            title = {
                Text(
                    text = stringResource(R.string.your_feed),
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colors.onBackground
                )
            },
            modifier = Modifier.fillMaxWidth(),
            navActions = {
                IconButton(
                    onClick = {
                        navigator.navigate(SearchScreenDestination)
                    }
                ) {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = stringResource(R.string.search),
                        tint = MaterialTheme.colors.onBackground
                    )
                }
            }
        )
        PostCard(
            post = Post(
                username = "Batman",
                imageUrl = R.drawable.batman_profile_image,
                profilePictureUrl = "",
                description = stringResource(id = R.string.test_string),
                likeCount = 17,
                commentCount = 7
            ),
            onPostClick = {
                navigator.navigate(PostDetailScreenDestination)
            }
        )
    }
}
