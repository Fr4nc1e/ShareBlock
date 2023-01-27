package com.code.block.feature.post.presentation.homescreen

import androidx.compose.foundation.layout.* // ktlint-disable no-wildcard-imports
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.material.* // ktlint-disable no-wildcard-imports
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.runtime.* // ktlint-disable no-wildcard-imports
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.code.block.R
import com.code.block.core.presentation.components.PostCard
import com.code.block.core.presentation.components.Screen
import com.code.block.core.presentation.components.StandardTopBar
import com.code.block.core.util.ShareManager.sharePost
import com.code.block.core.util.ui.UiEvent
import com.code.block.core.util.ui.asString
import kotlinx.coroutines.flow.collectLatest

@OptIn(ExperimentalMaterialApi::class)
@Suppress("OPT_IN_IS_NOT_ENABLED")
@Composable
fun HomeScreen(
    onNavigate: (String) -> Unit = {},
    scaffoldState: ScaffoldState,
    lazyListState: LazyListState,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val pagingState = viewModel.pagingState.value
    val context = LocalContext.current
    val refreshing by viewModel.isRefreshing.collectAsState()
    val pullRefreshState = rememberPullRefreshState(
        refreshing = refreshing,
        onRefresh = {
            viewModel.onEvent(HomeEvent.Refresh)
        }
    )

    LaunchedEffect(key1 = true) {
        viewModel.eventFlow.collectLatest { event ->
            when (event) {
                is UiEvent.SnackBarEvent -> {
                    scaffoldState.snackbarHostState.showSnackbar(
                        message = event.uiText.asString(context)
                    )
                }
                else -> Unit
            }
        }
    }

    Box(
        Modifier.fillMaxSize()
            .pullRefresh(state = pullRefreshState)
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
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize(),
                    state = lazyListState
                ) {
                    items(pagingState.items.size) { i ->
                        val post = pagingState.items[i]
                        if (
                            i >= pagingState.items.size - 1 &&
                            !pagingState.endReached &&
                            !pagingState.isLoading
                        ) {
                            viewModel.loadNextPosts()
                        }
                        PostCard(
                            onNavigate = onNavigate,
                            post = post,
                            comment = null,
                            onPostClick = {
                                onNavigate(Screen.PostDetailScreen.route + "/${post.id}")
                            },
                            onLikeClick = {
                                viewModel.onEvent(HomeEvent.LikedParent(post.id))
                            },
                            onCommentClick = {
                                onNavigate(Screen.PostDetailScreen.route + "/${post.id}?shouldShowKeyboard=true")
                            },
                            onShareClick = {
                                context.sharePost(postId = post.id)
                            }
                        )
                    }

                    item {
                        Spacer(modifier = Modifier.height(90.dp))
                    }
                }

                if (pagingState.isLoading) {
                    CircularProgressIndicator(
                        modifier = Modifier.align(Center)
                    )
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
