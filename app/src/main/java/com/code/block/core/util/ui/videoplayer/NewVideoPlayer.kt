package com.code.block.core.util.ui.videoplayer

import android.media.metrics.PlaybackStateEvent.STATE_ENDED
import android.net.Uri
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.* // ktlint-disable no-wildcard-imports
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import com.code.block.core.presentation.ui.theme.SpaceSmall
import com.google.android.exoplayer2.C.VIDEO_SCALING_MODE_SCALE_TO_FIT_WITH_CROPPING
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.MediaMetadata
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.ui.AspectRatioFrameLayout
import com.google.android.exoplayer2.ui.StyledPlayerView

@Composable
fun NewVideoPlayer(
    modifier: Modifier = Modifier,
    uri: Uri,
) {
    val context = LocalContext.current
    var lifecycle by remember {
        mutableStateOf(Lifecycle.Event.ON_CREATE)
    }
    val lifecycleOwner = LocalLifecycleOwner.current
    val exoPlayer = remember {
        ExoPlayer.Builder(context)
            .apply {
                setSeekBackIncrementMs(5 * 1000L)
                setSeekForwardIncrementMs(10 * 1000L)
            }
            .build()
            .apply {
                setMediaItem(
                    MediaItem.Builder()
                        .apply {
                            setUri(uri)
                            setMediaMetadata(
                                MediaMetadata.Builder()
                                    .setDisplayTitle("")
                                    .build(),
                            )
                        }
                        .build(),
                )
                prepare()
                playWhenReady = true
                repeatMode = Player.REPEAT_MODE_ONE
                videoScalingMode = VIDEO_SCALING_MODE_SCALE_TO_FIT_WITH_CROPPING
            }
    }
    var shouldShowControls by remember { mutableStateOf(false) }

    var isPlaying by remember { mutableStateOf(exoPlayer.isPlaying) }

    var totalDuration by remember { mutableStateOf(0L) }

    var currentTime by remember { mutableStateOf(0L) }

    var bufferedPercentage by remember { mutableStateOf(0) }

    var playbackState by remember { mutableStateOf(exoPlayer.playbackState) }

    var isMute by remember { mutableStateOf(false) }

    Box(
        modifier = modifier
            .padding(horizontal = SpaceSmall)
            .fillMaxSize()
            .clip(RoundedCornerShape(16.dp)),
    ) {
        DisposableEffect(lifecycleOwner) {
            val observer = LifecycleEventObserver { _, event ->
                lifecycle = event
            }
            lifecycleOwner.lifecycle.addObserver(observer)

            onDispose {
                lifecycleOwner.lifecycle.removeObserver(observer)
            }
        }
        DisposableEffect(key1 = Unit) {
            val listener =
                object : Player.Listener {
                    override fun onEvents(
                        player: Player,
                        events: Player.Events,
                    ) {
                        super.onEvents(player, events)
                        totalDuration = player.duration.coerceAtLeast(0L)
                        currentTime = player.currentPosition.coerceAtLeast(0L)
                        bufferedPercentage = player.bufferedPercentage
                        isPlaying = player.isPlaying
                        playbackState = player.playbackState
                    }
                }
            exoPlayer.addListener(listener)
            exoPlayer.volume = 0f

            onDispose {
                exoPlayer.removeListener(listener)
                exoPlayer.release()
            }
        }

        AndroidView(
            modifier = Modifier
                .clickable {
                    shouldShowControls = shouldShowControls.not()
                },
            factory = {
                StyledPlayerView(context).apply {
                    player = exoPlayer
                    useController = false
                    resizeMode = AspectRatioFrameLayout.RESIZE_MODE_ZOOM
                    layoutParams =
                        FrameLayout.LayoutParams(
                            ViewGroup.LayoutParams.MATCH_PARENT,
                            ViewGroup.LayoutParams.MATCH_PARENT,
                        )
                }
            },
        )

        PlayerController(
            modifier = Modifier.fillMaxSize().align(Alignment.BottomCenter),
            isVisible = { shouldShowControls },
            isPlaying = { isPlaying },
            onReplayClick = { exoPlayer.seekBack() },
            onForwardClick = { exoPlayer.seekForward() },
            onPauseToggle = {
                when {
                    exoPlayer.isPlaying -> {
                        // pause the video
                        exoPlayer.pause()
                    }
                    exoPlayer.isPlaying.not() &&
                        playbackState == STATE_ENDED -> {
                        exoPlayer.seekTo(0)
                        exoPlayer.playWhenReady = true
                    }
                    else -> {
                        exoPlayer.play()
                    }
                }
                isPlaying = isPlaying.not()
            },
            totalDuration = { totalDuration },
            currentTime = { currentTime },
            isMute = { isMute },
            bufferedPercentage = { bufferedPercentage },
            playbackState = { playbackState },
            onSeekChanged = { timeMs: Float ->
                exoPlayer.seekTo(timeMs.toLong())
            },
        ) {
            when (isMute) {
                true -> exoPlayer.volume = 0f
                false -> exoPlayer.volume = 0.5f
            }
            isMute = isMute.not()
        }
    }
}
