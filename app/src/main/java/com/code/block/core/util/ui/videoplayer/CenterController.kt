package com.code.block.core.util.ui.videoplayer

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.* // ktlint-disable no-wildcard-imports
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.media3.common.Player.STATE_ENDED

@Composable
fun CenterController(
    modifier: Modifier = Modifier,
    isPlaying: () -> Boolean,
    playbackState: () -> Int,
    onReplayClick: () -> Unit,
    onPauseToggle: () -> Unit,
    onForwardClick: () -> Unit,
) {
    val isVideoPlaying = remember(isPlaying()) { isPlaying() }

    val playerState = remember(playbackState()) { playbackState() }

    Row(modifier = modifier, horizontalArrangement = Arrangement.SpaceEvenly) {
        IconButton(
            modifier = Modifier.size(40.dp),
            onClick = onReplayClick,
        ) {
            Icon(
                modifier = Modifier.fillMaxSize(),
                imageVector = Icons.Default.Replay5,
                tint = MaterialTheme.colors.primary,
                contentDescription = "Replay 5 seconds",
            )
        }

        IconButton(
            modifier = Modifier.size(40.dp),
            onClick = onPauseToggle,
        ) {
            Icon(
                modifier = Modifier.fillMaxSize(),
                tint = MaterialTheme.colors.primary,
                imageVector =
                when {
                    isVideoPlaying -> {
                        Icons.Default.Pause
                    }
                    isVideoPlaying.not() && playerState == STATE_ENDED -> {
                        Icons.Default.Replay
                    }
                    else -> {
                        Icons.Default.PlayArrow
                    }
                },
                contentDescription = "Play/Pause",
            )
        }

        IconButton(
            modifier = Modifier.size(40.dp),
            onClick = onForwardClick,
        ) {
            Icon(
                modifier = Modifier.fillMaxSize(),
                tint = MaterialTheme.colors.primary,
                imageVector = Icons.Default.Forward10,
                contentDescription = "Forward 10 seconds",
            )
        }
    }
}
