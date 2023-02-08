package com.code.block.core.util.ui.multilfab

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Camera
import androidx.compose.material.icons.filled.Photo
import androidx.compose.material.icons.filled.VideoFile

val fabItems = listOf(
    MultiFabItem(
        icon = Icons.Default.Photo,
        label = "photo",
    ),
    MultiFabItem(
        icon = Icons.Default.VideoFile,
        label = "video",
    ),
    MultiFabItem(
        icon = Icons.Default.Camera,
        label = "camera",
    ),
)
