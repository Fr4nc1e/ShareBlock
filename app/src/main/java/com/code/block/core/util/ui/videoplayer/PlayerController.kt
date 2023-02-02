package com.code.block.core.util.ui.videoplayer

import androidx.compose.animation.* // ktlint-disable no-wildcard-imports
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun PlayerController(
    modifier: Modifier = Modifier,
    isVisible: () -> Boolean,
    isPlaying: () -> Boolean,
    onReplayClick: () -> Unit,
    onForwardClick: () -> Unit,
    onPauseToggle: () -> Unit,
    totalDuration: () -> Long,
    currentTime: () -> Long,
    isMute: () -> Boolean,
    bufferedPercentage: () -> Int,
    playbackState: () -> Int,
    onSeekChanged: (timeMs: Float) -> Unit,
    onMuteClick: () -> Unit
) {
    val visible = remember(isVisible()) { isVisible() }

    AnimatedVisibility(
        modifier = modifier,
        visible = visible,
        enter = fadeIn(),
        exit = fadeOut()
    ) {
        Box(modifier = Modifier.background(Color.Black.copy(alpha = 0.6f))) {
            CenterController(
                modifier = Modifier.align(Alignment.Center).fillMaxWidth(),
                isPlaying = isPlaying,
                onReplayClick = onReplayClick,
                onForwardClick = onForwardClick,
                onPauseToggle = onPauseToggle,
                playbackState = playbackState
            )

            BottomController(
                modifier =
                Modifier.align(Alignment.BottomCenter)
                    .fillMaxWidth()
                    .animateEnterExit(
                        enter =
                        slideInVertically(
                            initialOffsetY = { fullHeight: Int ->
                                fullHeight
                            }
                        ),
                        exit =
                        slideOutVertically(
                            targetOffsetY = { fullHeight: Int ->
                                fullHeight
                            }
                        )
                    ),
                totalDuration = totalDuration,
                currentTime = currentTime,
                isMute = isMute,
                bufferedPercentage = bufferedPercentage,
                onSeekChanged = onSeekChanged,
                onMuteClick = onMuteClick
            )
        }
    }
}
