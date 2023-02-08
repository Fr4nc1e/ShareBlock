package com.code.block.core.util.ui

import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.code.block.core.presentation.ui.theme.SpaceSmall
import com.code.block.core.util.ui.videoplayer.NewVideoPlayer

@Composable
fun ContentLoader(
    contentUrl: String,
    modifier: Modifier = Modifier,
) {
    if (contentUrl.takeLastWhile { it != '.' } != "mp4") {
        val painter = rememberAsyncImagePainter(
            ImageRequest.Builder(LocalContext.current)
                .data(data = contentUrl)
                .apply(block = fun ImageRequest.Builder.() {
                    crossfade(true)
                }).build(),
        )
        Image(
            painter = painter,
            contentDescription = null,
            modifier = modifier
                .fillMaxSize()
                .aspectRatio(1f)
                .padding(horizontal = SpaceSmall)
                .clip(RoundedCornerShape(16.dp)),
            contentScale = ContentScale.Crop,
        )
    } else {
        NewVideoPlayer(
            modifier = modifier,
            uri = Uri.parse(contentUrl),
        )
    }
}
