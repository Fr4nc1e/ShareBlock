package com.code.block.feature.chat.presentation.chatscreen

import android.util.Base64
import androidx.compose.foundation.layout.* // ktlint-disable no-wildcard-imports
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.hilt.navigation.compose.hiltViewModel
import com.code.block.R
import com.code.block.core.presentation.components.Screen
import com.code.block.core.presentation.components.StandardTopBar
import com.code.block.core.presentation.ui.theme.SpaceLarge
import com.code.block.core.presentation.ui.theme.SpaceSmall
import com.code.block.feature.chat.presentation.chatscreen.component.ChatItem

@Composable
fun ChatScreen(
    onNavigate: (String) -> Unit = {},
    viewModel: ChatViewModel = hiltViewModel(),
) {
    val chats = viewModel.state.value.chats
    val isLoading = viewModel.state.value.isLoading

    Column(
        modifier = Modifier.fillMaxSize(),
    ) {
        StandardTopBar(
            title = {
                Text(
                    text = stringResource(R.string.chat),
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colors.onBackground,
                )
            },
            modifier = Modifier.fillMaxWidth(),
        )

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
        ) {
            items(chats) { chat ->
                ChatItem(
                    modifier = Modifier.padding(SpaceSmall),
                    chatItem = chat,
                    onItemClick = {
                        onNavigate(
                            Screen.MessageScreen.route +
                                "/${chat.remoteUserId}" +
                                "/${chat.remoteUsername}" +
                                "/${Base64.encodeToString(
                                    /* input = */ chat.remoteUserProfilePictureUrl.encodeToByteArray(),
                                    /* flags = */ 0,
                                )}?chatId=${chat.chatId}",
                        )
                    },
                )
            }
            item {
                Spacer(modifier = Modifier.height(SpaceLarge))
            }
        }
    }
}
