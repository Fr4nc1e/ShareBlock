package com.code.block.presentation.splashscreen

import android.view.animation.OvershootInterpolator
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.code.block.R
import com.code.block.presentation.destinations.LoginScreenDestination
import com.code.block.presentation.destinations.SplashScreenDestination
import com.code.block.utils.Constants
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext

@Destination(start = true)
@Composable
fun SplashScreen(
    navigator: DestinationsNavigator
) {
    val scale = remember {
        Animatable(0f)
    }

    val overshootInterpolator = remember {
        OvershootInterpolator(3f)
    }

    LaunchedEffect(key1 = true) {
        withContext(Dispatchers.Main) {
            scale.animateTo(
                targetValue = 0.5f,
                animationSpec = tween(
                    durationMillis = 1000,
                    easing = {
                        overshootInterpolator.getInterpolation(it)
                    }
                )
            )
            delay(Constants.SPLASH_SCREEN_DELAY)
            navigator.navigate(LoginScreenDestination) {
                popUpTo(SplashScreenDestination.route) {
                    inclusive = true
                }
            }
        }
    }
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.bat_man_front),
            contentDescription = "Logo",
            modifier = Modifier.scale(scale.value)
                .size(200.dp)
        )
    }
}
