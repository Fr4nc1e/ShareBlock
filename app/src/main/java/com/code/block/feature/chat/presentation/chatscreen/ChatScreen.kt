package com.code.block.feature.chat.presentation.chatscreen

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import com.code.block.R
import com.code.block.core.presentation.components.StandardTopBar

@Composable
fun ChatScreen() {
    StandardTopBar(
        title = {
            Text(
                text = stringResource(R.string.chat),
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colors.onBackground
            )
        },
        modifier = Modifier.fillMaxWidth()
    )
}
