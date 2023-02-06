package com.code.block.feature.chat.presentation.messagescreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.* // ktlint-disable no-wildcard-imports
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.* // ktlint-disable no-wildcard-imports
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Send
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.code.block.R
import com.code.block.core.presentation.components.Screen
import com.code.block.core.presentation.components.StandardTextField
import com.code.block.core.presentation.components.StandardTopBar
import com.code.block.core.presentation.ui.theme.ProfilePictureSizeSmall
import com.code.block.core.presentation.ui.theme.SpaceLarge
import com.code.block.core.presentation.ui.theme.SpaceMedium
import com.code.block.core.util.ui.UiEvent
import com.code.block.feature.chat.presentation.messagescreen.components.MessageItem
import com.code.block.feature.chat.presentation.messagescreen.event.MessageEvent
import com.code.block.feature.chat.presentation.messagescreen.event.MessageUpdateEvent
import kotlinx.coroutines.flow.collectLatest
import okio.ByteString.Companion.decodeBase64
import java.nio.charset.Charset

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
                is MessageUpdateEvent.MessagePageLoaded -> {
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

    Box(modifier = Modifier.fillMaxSize()) {
        Column(modifier = Modifier.fillMaxSize()) {
            StandardTopBar(
                title = {
                    Image(
                        painter = rememberAsyncImagePainter(
                            ImageRequest.Builder(LocalContext.current)
                                .data(data = decodedRemoteUserProfilePictureUrl)
                                .apply(
                                    block = fun ImageRequest.Builder.() {
                                        crossfade(true)
                                    }
                                ).build()
                        ),
                        contentDescription = stringResource(R.string.profile_pic),
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .size(ProfilePictureSizeSmall)
                            .clip(CircleShape)
                            .border(
                                width = 1.dp,
                                brush = Brush.sweepGradient(
                                    listOf(
                                        Color(0xFF9575CD),
                                        Color(0xFFBA68C8),
                                        Color(0xFFE57373),
                                        Color(0xFFFFB74D),
                                        Color(0xFFFFF176),
                                        Color(0xFFAED581),
                                        Color(0xFF4DD0E1),
                                        Color(0xFF9575CD)
                                    )
                                ),
                                shape = CircleShape
                            )
                            .clickable {
                                onNavigate(Screen.ProfileScreen.route + "?userId=$remoteUserId")
                            }
                    )
                    Spacer(modifier = Modifier.width(SpaceMedium))
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
                        } else Alignment.CenterStart
                    ) {
                        MessageItem(
                            message = message,
                            isOwnMessage = isOwnMessage
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
                    .clip(MaterialTheme.shapes.medium)
                    .background(color = MaterialTheme.colors.background),
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
                            } else MaterialTheme.colors.background,
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
