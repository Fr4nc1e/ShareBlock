package com.code.block.core.presentation.components

import androidx.compose.animation.core.* // ktlint-disable no-wildcard-imports
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import com.code.block.core.domain.state.LikedStates

@Composable
fun DoubleTapToLike(
    modifier: Modifier = Modifier,
    transitionState: MutableTransitionState<LikedStates>,
) {
    if (transitionState.currentState == LikedStates.Initial) {
        transitionState.targetState = LikedStates.Liked
    } else if (transitionState.currentState == LikedStates.Liked) {
        transitionState.targetState = LikedStates.Disappeared
    }

    val transition = updateTransition(transitionState = transitionState, label = null)
    val alpha by transition.animateFloat(
        transitionSpec = {
            when {
                LikedStates.Initial isTransitioningTo LikedStates.Liked ->
                    keyframes {
                        durationMillis = 500
                        0f at 0
                        0.5f at 100
                        1f at 225
                    }
                LikedStates.Liked isTransitioningTo LikedStates.Disappeared ->
                    tween(durationMillis = 200)
                else -> snap()
            }
        },
        label = "",
    ) {
        if (it == LikedStates.Liked) 1f else 0f
    }

    val scale by transition.animateFloat(
        transitionSpec = {
            when {
                LikedStates.Initial isTransitioningTo LikedStates.Liked ->
                    spring(dampingRatio = Spring.DampingRatioHighBouncy)
                LikedStates.Liked isTransitioningTo LikedStates.Disappeared ->
                    tween(200)
                else -> snap()
            }
        },
        label = "",
    ) {
        when (it) {
            LikedStates.Initial -> 0f
            LikedStates.Liked -> 4f
            LikedStates.Disappeared -> 2f
        }
    }

    Icon(
        imageVector = Icons.Filled.Favorite,
        contentDescription = null,
        modifier = modifier
            .graphicsLayer(
                alpha = alpha,
                scaleX = scale,
                scaleY = scale,
            ),
        tint = Color(0xFFE91E63),
    )
}
