package com.code.block.presentation.mainfeedscreen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
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
import com.code.block.domain.util.DateFormattedUtil
import com.code.block.presentation.components.StandardTopBar
import com.code.block.presentation.destinations.PostDetailScreenDestination
import com.code.block.presentation.destinations.SearchScreenDestination
import com.code.block.presentation.mainfeedscreen.components.PostCard
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

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
        ) {
            items(20) {
                PostCard(
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
                    onPostClick = {
                        navigator.navigate(PostDetailScreenDestination)
                    }
                )
            }
        }
    }
}
