package com.code.block.feature.profile.presentation.profilescreen.components.tablayout

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import com.code.block.core.domain.model.Post
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.PagerState

@OptIn(ExperimentalPagerApi::class)
@Composable
fun TabsContent(
    scrollState: ScrollState,
    pagerState: PagerState,
    post: Post,
    onNavigate: (String) -> Unit = {}
) {
    HorizontalPager(
        state = pagerState,
        modifier = Modifier
            .fillMaxHeight()
            .nestedScroll(
                remember {
                    object : NestedScrollConnection {
                        override fun onPreScroll(
                            available: Offset,
                            source: NestedScrollSource
                        ): Offset {
                            return if (available.y > 0) {
                                Offset.Zero
                            } else {
                                Offset(
                                    x = 0f,
                                    y = -scrollState.dispatchRawDelta(-available.y)
                                )
                            }
                        }
                    }
                }
            )
    ) { page ->
        when (page) {
            0 -> ProfilePostScreen(
                post = post,
                onNavigate = onNavigate
            )
            1 -> ProfileCommentScreen(
                post = post,
                onNavigate = onNavigate
            )
            2 -> ProfilePostScreen(
                post = post,
                onNavigate = onNavigate
            )
        }
    }
}
