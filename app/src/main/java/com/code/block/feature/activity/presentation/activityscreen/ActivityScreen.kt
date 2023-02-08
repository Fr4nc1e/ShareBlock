package com.code.block.feature.activity.presentation.activityscreen

import androidx.compose.foundation.layout.* // ktlint-disable no-wildcard-imports
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import com.code.block.R
import com.code.block.core.domain.model.Activity
import com.code.block.core.presentation.components.StandardTopBar
import com.code.block.core.presentation.ui.theme.SpaceMedium
import com.code.block.feature.activity.presentation.activityscreen.components.ActivityItem

@Suppress("OPT_IN_IS_NOT_ENABLED")
@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ActivityScreen(
    onNavigate: (String) -> Unit = {},
    viewModel: ActivityViewModel = hiltViewModel(),
) {
    val state = viewModel.state.value
    val activities = viewModel.activities.collectAsLazyPagingItems()
    val refreshing by viewModel.isRefreshing.collectAsState()
    val pullRefreshState = rememberPullRefreshState(
        refreshing = refreshing,
        onRefresh = {
            viewModel.refresh {
                activities.refresh()
            }
        },
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .pullRefresh(pullRefreshState),
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
        ) {
            StandardTopBar(
                title = {
                    Text(
                        text = stringResource(R.string.activity),
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colors.onBackground,
                    )
                },
                modifier = Modifier.fillMaxWidth(),
            )

            LazyColumn(
                modifier = Modifier
                    .fillMaxSize(),
                contentPadding = PaddingValues(SpaceMedium),
            ) {
                items(activities) { activity ->
                    activity?.let {
                        ActivityItem(
                            activity = Activity(
                                userId = activity.userId,
                                username = activity.username,
                                profileImageUrl = activity.profileImageUrl,
                                activityType = activity.activityType,
                                parentId = activity.parentId,
                                formattedTime = activity.formattedTime,
                            ),
                            onUserClick = onNavigate,
                            onActivityClick = onNavigate,
                        )
                    }
                }
            }
        }
        if (state.isLoading) {
            CircularProgressIndicator(
                modifier = Modifier.align(Alignment.Center),
            )
        }

        PullRefreshIndicator(
            refreshing = refreshing,
            state = pullRefreshState,
            modifier = Modifier.align(Alignment.TopCenter),
        )
    }
}
