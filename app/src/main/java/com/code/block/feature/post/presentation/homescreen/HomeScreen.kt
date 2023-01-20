package com.code.block.feature.post.presentation.homescreen

import androidx.compose.foundation.layout.* // ktlint-disable no-wildcard-imports
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.material.* // ktlint-disable no-wildcard-imports
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowUpward
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.runtime.* // ktlint-disable no-wildcard-imports
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.items
import com.code.block.R
import com.code.block.core.domain.model.Post
import com.code.block.core.presentation.components.PostCard
import com.code.block.core.presentation.components.Screen
import com.code.block.core.presentation.components.StandardTopBar
import com.code.block.core.presentation.ui.theme.IconSizeLarge
import com.code.block.core.presentation.ui.theme.SpaceSmall
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun HomeScreen(
    scaffoldState: ScaffoldState,
    onNavigate: (String) -> Unit = {},
    lazyListState: LazyListState,
    posts: LazyPagingItems<Post>,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val state = viewModel.state.value
    val scope = rememberCoroutineScope()
    val refreshing by viewModel.isRefreshing.collectAsState()
    val pullRefreshState = rememberPullRefreshState(
        refreshing = refreshing,
        onRefresh = {
            viewModel.refresh {
                posts.refresh()
            }
        }
    )

    Box(
        Modifier.pullRefresh(pullRefreshState)
    ) {
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
                            onNavigate(Screen.SearchScreen.route)
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
                        .fillMaxSize(),
                    state = lazyListState
                ) {
                    items(posts) { post ->
                        PostCard(
                            onNavigate = onNavigate,
                            post = Post(
                                username = post?.username ?: "Batman",
                                contentUrl = "http://172.28.211.51:8081/post_contents/" +
                                    post?.contentUrl?.takeLastWhile { it != '/' },
                                profilePictureUrl = post?.profilePictureUrl ?: "",
                                description = post?.description ?: "",
                                likeCount = post?.likeCount ?: 0,
                                commentCount = post?.commentCount ?: 0,
                                timestamp = post?.timestamp ?: 0
                            ),
                            onPostClick = {
                                onNavigate(Screen.PostDetailScreen.route)
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

                if (remember { derivedStateOf { lazyListState.firstVisibleItemIndex } }.value > 0) {
                    FloatingActionButton(
                        onClick = {
                            scope.launch {
                                lazyListState.animateScrollToItem(index = 0)
                            }
                        },
                        backgroundColor = MaterialTheme.colors.surface,
                        contentColor = MaterialTheme.colors.onSurface,
                        modifier = Modifier
                            .size(IconSizeLarge)
                            .align(Alignment.BottomEnd)
                            .padding(
                                end = SpaceSmall,
                                bottom = SpaceSmall
                            )
                    ) {
                        Icon(
                            imageVector = Icons.Default.ArrowUpward,
                            contentDescription = "Back to top."
                        )
                    }
                }
            }
        }
        PullRefreshIndicator(
            refreshing = refreshing,
            state = pullRefreshState,
            modifier = Modifier.align(Alignment.TopCenter)
        )
    }
}
