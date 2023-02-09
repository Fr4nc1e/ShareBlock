package com.code.block.core.util

import android.graphics.Bitmap
import androidx.compose.ui.graphics.Color
import androidx.palette.graphics.Palette

object PaletteGenerator {
    fun generateDominateColor(
        bitmap: Bitmap?,
    ): List<Color?> {
        val palette = bitmap?.let { Palette.from(it).generate() }
        val color = palette?.dominantSwatch?.rgb?.let { Color(it) }
        val textColor = palette?.vibrantSwatch?.bodyTextColor?.let { Color(it) }
        return listOf(color, textColor)
    }
}
