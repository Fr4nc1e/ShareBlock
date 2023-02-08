package com.code.block.core.util.ui.multilfab

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector

data class MultiFabItem(
    val icon: ImageVector,
    val label: String,
    val srcIconColor: Color = Color.White,
    val labelTextColor: Color = Color.White,
    val labelBackgroundColor: Color = Color.Black.copy(alpha = 0.6F),
    val fabBackgroundColor: Color = Color.Unspecified,
)
