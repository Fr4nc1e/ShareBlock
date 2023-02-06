package com.code.block.core.util

import android.net.Uri
import android.webkit.MimeTypeMap
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.code.block.core.presentation.ui.theme.SpaceSmall
import com.code.block.core.util.ui.videoplayer.NewVideoPlayer

@Composable
fun ContentPreviewer(contentUri: Uri?) {
    Box(
        modifier = Modifier
            .aspectRatio(1f)
            .fillMaxSize()
            .padding(SpaceSmall)
            .clip(MaterialTheme.shapes.medium)
    ) {
        contentUri?.let { uri ->
            val fileExtension = MimeTypeMap
                .getFileExtensionFromUrl(uri.toString())
            val mimeType = MimeTypeMap
                .getSingleton()
                .getMimeTypeFromExtension(fileExtension)

            if (mimeType != null && !mimeType.contains("video")) {
                Image(
                    painter = rememberAsyncImagePainter(
                        model = ImageRequest.Builder(LocalContext.current)
                            .data(uri)
                            .build()
                    ),
                    contentDescription = null,
                    modifier = Modifier.matchParentSize()
                )
            } else {
                NewVideoPlayer(uri = contentUri)
            }
        }
    }
}
