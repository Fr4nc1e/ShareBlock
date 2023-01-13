package com.code.block.feature.post.presentation.homescreen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.* // ktlint-disable no-wildcard-imports
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import com.code.block.R
import com.code.block.core.domain.model.Post
import com.code.block.core.presentation.components.StandardTopBar
import com.code.block.feature.destinations.PostDetailScreenDestination
import com.code.block.feature.destinations.SearchScreenDestination
import com.code.block.feature.post.presentation.homescreen.components.PostCard
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import kotlinx.coroutines.launch

@Destination
@Composable
fun HomeScreen(
    navigator: DestinationsNavigator,
    scaffoldState: ScaffoldState,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val posts = viewModel.posts.collectAsLazyPagingItems()
    val state = viewModel.state.value
    val scope = rememberCoroutineScope()

    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        StandardTopBar(
            title = {
                Text(
                    text = stringResource(R.string.home),
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

        Box(modifier = Modifier.fillMaxSize()) {
            if (state.isLoadingFirstTime) {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center)
                )
            }

            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                items(posts) { post ->
                    PostCard(
                        navigator = navigator,
                        post = Post(
                            username = post?.username ?: "",
                            imageUrl = post?.imageUrl ?: "",
                            profilePictureUrl = post?.profilePictureUrl ?: "",
                            description = post?.description ?: "",
                            likeCount = post?.likeCount ?: 0,
                            commentCount = post?.commentCount ?: 0,
                            timestamp = post?.timestamp ?: 0
                        ),
                        onPostClick = {
                            navigator.navigate(PostDetailScreenDestination)
                        }
                    )
                }

                item {
                    if (state.isLoadingNewPosts) {
                        CircularProgressIndicator(
                            modifier = Modifier.align(Alignment.BottomCenter)
                        )
                    }
                }

                posts.apply {
                    when {
                        loadState.refresh !is LoadState.Loading -> {
                            viewModel.onEvent(HomeEvent.LoadPage)
                        }
                        loadState.append is LoadState.Loading -> {
                            viewModel.onEvent(HomeEvent.LoadPosts)
                        }
                        loadState.append is LoadState.NotLoading -> {
                            viewModel.onEvent(HomeEvent.LoadPage)
                        }
                        loadState.append is LoadState.Error -> {
                            scope.launch {
                                scaffoldState.snackbarHostState.showSnackbar(
                                    message = "Error"
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
