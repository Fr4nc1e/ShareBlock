package com.code.block.feature.chat.presentation.chatscreen

import androidx.compose.foundation.layout.* // ktlint-disable no-wildcard-imports
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.code.block.R
import com.code.block.core.presentation.components.Screen
import com.code.block.core.presentation.components.StandardTopBar
import com.code.block.core.presentation.ui.theme.SpaceLarge
import com.code.block.feature.chat.domain.model.Chat
import com.code.block.feature.chat.presentation.chatscreen.component.ChatItem

@Composable
fun ChatScreen(
    onNavigate: (String) -> Unit = {}
) {
    val chats = remember {
        listOf(
            Chat(
                username = "Francle",
                profileUrl = "http://172.28.211.51:8081/profile_pictures/a060d923-61ea-4302-bfde-b3e171da4601.jpg",
                latestMessage = "This is the last message of the chat with Philipp",
                latestFormattedTime = "19:39"
            ),
            Chat(
                username = "Francle",
                profileUrl = "http://172.28.211.51:8081/profile_pictures/a060d923-61ea-4302-bfde-b3e171da4601.jpg",
                latestMessage = "This is the last message of the chat with Philipp",
                latestFormattedTime = "19:39"
            ),
            Chat(
                username = "Francle",
                profileUrl = "http://172.28.211.51:8081/profile_pictures/a060d923-61ea-4302-bfde-b3e171da4601.jpg",
                latestMessage = "This is the last message of the chat with Philipp",
                latestFormattedTime = "19:39"
            )
        )
    }

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
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

        LazyColumn(
            modifier = Modifier.fillMaxSize()
        ) {
            item {
                Divider(
                    color = MaterialTheme.colors.onSurface,
                    thickness = 2.dp
                )
            }
            items(chats) { chat ->
                ChatItem(
                    chatItem = chat,
                    onItemClick = {
                        onNavigate(Screen.MessageScreen.route)
                    }
                )
            }
            item {
                Spacer(modifier = Modifier.height(SpaceLarge))
            }
        }
    }
}
