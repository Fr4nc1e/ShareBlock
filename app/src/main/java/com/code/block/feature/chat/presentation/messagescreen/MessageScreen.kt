package com.code.block.feature.chat.presentation.messagescreen

import androidx.compose.foundation.layout.* // ktlint-disable no-wildcard-imports
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.* // ktlint-disable no-wildcard-imports
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Send
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.code.block.R
import com.code.block.core.presentation.components.Screen
import com.code.block.core.presentation.components.StandardTextField
import com.code.block.core.presentation.components.StandardTopBar
import com.code.block.core.presentation.ui.theme.SpaceLarge
import com.code.block.core.presentation.ui.theme.SpaceMedium
import com.code.block.core.util.ui.UiEvent
import com.code.block.feature.chat.presentation.messagescreen.components.MessageItem
import com.code.block.feature.chat.presentation.messagescreen.event.MessageEvent
import com.code.block.feature.chat.presentation.messagescreen.event.MessageUpdateEvent
import java.nio.charset.Charset
import kotlinx.coroutines.flow.collectLatest
import okio.ByteString.Companion.decodeBase64

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun MessageScreen(
    remoteUserId: String,
    remoteUsername: String,
    encodedRemoteUserProfilePictureUrl: String,
    onNavigate: (String) -> Unit = {},
    viewModel: MessageViewModel = hiltViewModel()
) {
    val decodedRemoteUserProfilePictureUrl = remember {
        encodedRemoteUserProfilePictureUrl.decodeBase64()?.string(Charset.defaultCharset())
    }
    val ownProfilePicture = viewModel.ownProfilePicture.collectAsState(initial = null)
    val ownUserId = viewModel.ownUserId
    val pagingState = viewModel.pagingState.value
    val state = viewModel.state.value
    val focusRequester = remember { FocusRequester() }
    val messageTextFiledState = viewModel.messageTextFieldState.value
    val lazyListState = rememberLazyListState()
    val keyboardController = LocalSoftwareKeyboardController.current

    LaunchedEffect(key1 = true) {
        viewModel.eventFlow.collectLatest {
            when (it) {
                UiEvent.SendMessage -> {
                    viewModel.sendMessage()
                }
                else -> Unit
            }
        }
    }
    LaunchedEffect(key1 = pagingState, key2 = keyboardController) {
        viewModel.messageReceived.collect { event ->
            when (event) {
                is MessageUpdateEvent.SingleMessageUpdate,
                is MessageUpdateEvent.MessagePageLoaded
                -> {
                    if (pagingState.items.isEmpty()) {
                        return@collect
                    }
                    lazyListState.scrollToItem(pagingState.items.size - 1)
                }
                is MessageUpdateEvent.MessageSent -> {
                    keyboardController?.hide()
                }
            }
        }
    }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            StandardTopBar(
                title = {
                    Text(
                        text = remoteUsername,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colors.onBackground
                    )
                }
            )

            LazyColumn(
                modifier = Modifier
                    .padding(paddingValues = PaddingValues(horizontal = 16.dp))
                    .weight(1f)
                    .fillMaxWidth(),
                state = lazyListState,
                contentPadding = PaddingValues(vertical = SpaceLarge)
            ) {
                item { Spacer(modifier = Modifier.height(32.dp)) }

                items(pagingState.items.size) { i ->
                    val message = pagingState.items[i]
                    val isOwnMessage = message.fromId != remoteUserId
                    if (i >= pagingState.items.size - 1 &&
                        !pagingState.endReached &&
                        !pagingState.isLoading
                    ) {
                        viewModel.loadNextMessages()
                    }
                    Box(
                        modifier = Modifier.fillMaxWidth(),
                        contentAlignment = if (isOwnMessage) {
                            Alignment.CenterEnd
                        } else {
                            Alignment.CenterStart
                        }
                    ) {
                        MessageItem(
                            message = message,
                            remoteProfileUrl = decodedRemoteUserProfilePictureUrl,
                            ownProfilePictureUrl = ownProfilePicture.value,
                            isOwnMessage = isOwnMessage,
                            onOwnUserClick = {
                                onNavigate(Screen.ProfileScreen.route + "?userId=$ownUserId")
                            },
                            onRemoteUserClick = {
                                onNavigate(Screen.ProfileScreen.route + "?userId=$remoteUserId")
                            }
                        )
                    }

                    Spacer(modifier = Modifier.height(32.dp))
                }
            }

            StandardTextField(
                text = messageTextFiledState.text,
                onValueChange = {
                    viewModel.onEvent(MessageEvent.EnteredMessage(it))
                },
                modifier = Modifier
                    .padding(SpaceMedium)
                    .focusRequester(focusRequester = focusRequester)
                    .clip(MaterialTheme.shapes.medium),
                hint = stringResource(id = R.string.chat),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Done
                ),
                trailingIcon = {
                    IconButton(
                        onClick = {
                            viewModel.onEvent(MessageEvent.SendMessage)
                        },
                        enabled = messageTextFiledState.error == null || !state.canSendMessage
                    ) {
                        Icon(
                            imageVector = Icons.Default.Send,
                            tint = if (messageTextFiledState.error == null && state.canSendMessage) {
                                MaterialTheme.colors.primary
                            } else {
                                MaterialTheme.colors.background
                            },
                            contentDescription = stringResource(id = R.string.send_comment)
                        )
                    }
                }
            )
        }
        if (state.isLoading) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
        }
    }
}
