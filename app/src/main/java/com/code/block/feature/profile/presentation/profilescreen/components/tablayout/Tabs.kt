package com.code.block.feature.profile.presentation.profilescreen.components.tablayout

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.* // ktlint-disable no-wildcard-imports
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Comment
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.icons.outlined.Newspaper
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.PagerState
import kotlinx.coroutines.launch

@OptIn(ExperimentalPagerApi::class)
@Composable
fun Tabs(
    pagerState: PagerState,
) {
    val tabList = listOf(
        "Post" to Icons.Outlined.Newspaper,
        "Comment" to Icons.Outlined.Comment,
        "Favorite" to Icons.Outlined.Favorite,
    )

    val scope = rememberCoroutineScope()

    TabRow(
        modifier = Modifier
            .padding(vertical = 4.dp, horizontal = 8.dp)
            .clip(RoundedCornerShape(50))
            .border(
                width = 1.dp,
                color = MaterialTheme.colors.primary,
                shape = RoundedCornerShape(50),
            ),
        selectedTabIndex = pagerState.currentPage,
        backgroundColor = MaterialTheme.colors.primary,
        indicator = { Box {} },
    ) {
        tabList.forEachIndexed { index, _ ->
            val selected = pagerState.currentPage == index
            Tab(
                modifier = if (selected) {
                    Modifier
                        .clip(RoundedCornerShape(50))
                        .background(
                            MaterialTheme.colors.onPrimary,
                        )
                } else {
                    Modifier
                        .clip(RoundedCornerShape(50))
                        .background(
                            MaterialTheme.colors.primary,
                        )
                },
                selected = selected,
                selectedContentColor = MaterialTheme.colors.primary,
                unselectedContentColor = MaterialTheme.colors.onPrimary,
                onClick = {
                    scope.launch {
                        pagerState.animateScrollToPage(index)
                    }
                },
                icon = {
                    Icon(
                        imageVector = tabList[index].second,
                        contentDescription = null,
                    )
                },
            )
        }
    }
}
