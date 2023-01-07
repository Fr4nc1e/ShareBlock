package com.code.block.presentation.editprofilescreen.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.* // ktlint-disable no-wildcard-imports
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.code.block.ui.theme.SpaceSmall
import com.google.accompanist.flowlayout.FlowRow
import com.google.accompanist.flowlayout.MainAxisAlignment

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ChipContent(
    list: List<String>,
    modifier: Modifier = Modifier,
    selected: Boolean = false,
    selectedColor: Color = MaterialTheme.colors.primary,
    unSelectedColor: Color = MaterialTheme.colors.onSurface,
    onChipClick: () -> Unit = {}
) {
    FlowRow(
        modifier = modifier,
        mainAxisAlignment = MainAxisAlignment.Center,
        mainAxisSpacing = SpaceSmall,
        crossAxisSpacing = SpaceSmall
    ) {
        list.forEach {
            Chip(
                onClick = {
                    onChipClick()
                },
                modifier = Modifier.padding(paddingValues = PaddingValues(SpaceSmall)),
                shape = RoundedCornerShape(50.dp),
                border = BorderStroke(
                    width = 1.dp,
                    color = if (selected) selectedColor else unSelectedColor
                )
            ) {
                Text(
                    text = it,
                    modifier = Modifier.align(CenterVertically),
                    color = if (selected) selectedColor else unSelectedColor
                )
            }
        }
    }
}
