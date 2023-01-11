package com.code.block.feature.profile.presentation.profilescreen.components.tablayout

import androidx.compose.material.* // ktlint-disable no-wildcard-imports
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Comment
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.icons.outlined.Newspaper
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.PagerState
import com.google.accompanist.pager.pagerTabIndicatorOffset
import kotlinx.coroutines.launch

@OptIn(ExperimentalPagerApi::class)
@Composable
fun Tabs(
    pagerState: PagerState
) {
    val tabList = listOf(
        "Post" to Icons.Outlined.Newspaper,
        "Comment" to Icons.Outlined.Comment,
        "Favorite" to Icons.Outlined.Favorite
    )

    val scope = rememberCoroutineScope()

    TabRow(
        selectedTabIndex = pagerState.currentPage,
        backgroundColor = MaterialTheme.colors.background,
        contentColor = MaterialTheme.colors.onSurface,
        indicator = {
            TabRowDefaults.Indicator(
                Modifier.pagerTabIndicatorOffset(
                    pagerState = pagerState,
                    tabPositions = it
                ),
                height = 2.dp,
                color = MaterialTheme.colors.primary
            )
        }
    ) {
        tabList.forEachIndexed { index, _ ->
            Tab(
                selected = pagerState.currentPage == index,
                selectedContentColor = MaterialTheme.colors.primary,
                unselectedContentColor = MaterialTheme.colors.onSurface,
                onClick = {
                    scope.launch {
                        pagerState.animateScrollToPage(index)
                    }
                },
                icon = {
                    Icon(
                        imageVector = tabList[index].second,
                        contentDescription = null
                    )
                },
                text = {
                    Text(
                        text = tabList[index].first,
                        color = if (pagerState.currentPage == index) {
                            MaterialTheme.colors.primary
                        } else MaterialTheme.colors.onSurface
                    )
                }
            )
        }
    }
}
