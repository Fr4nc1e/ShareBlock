package com.code.block.presentation.profilescreen.components.tablayout

import androidx.compose.runtime.Composable
import com.code.block.domain.model.Post
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.PagerState
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@OptIn(ExperimentalPagerApi::class)
@Composable
fun TabsContent(
    pagerState: PagerState,
    post: Post,
    navigator: DestinationsNavigator
) {
    HorizontalPager(state = pagerState) {
            page ->
        when (page) {
            0 -> TabContentScreen(
                post = post,
                navigator = navigator
            )
        }
    }
}
