package com.code.block.feature.post.presentation.postdetailscreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.* // ktlint-disable no-wildcard-imports
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.* // ktlint-disable no-wildcard-imports
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Send
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction.Companion.Done
import androidx.compose.ui.text.input.KeyboardType
import androidx.hilt.navigation.compose.hiltViewModel
import com.code.block.R
import com.code.block.core.presentation.components.ActionRow
import com.code.block.core.presentation.components.InteractiveButtons
import com.code.block.core.presentation.components.StandardTextField
import com.code.block.core.presentation.components.StandardTopBar
import com.code.block.core.presentation.ui.theme.ProfilePictureSizeSmall
import com.code.block.core.presentation.ui.theme.SpaceLarge
import com.code.block.core.presentation.ui.theme.SpaceMedium
import com.code.block.core.presentation.ui.theme.SpaceSmall
import com.code.block.core.util.KeyboardShower.showKeyboard
import com.code.block.core.util.ShareManager.sharePost
import com.code.block.core.util.ui.ContentLoader
import com.code.block.core.util.ui.UiEvent
import com.code.block.core.util.ui.asString
import com.code.block.feature.post.presentation.postdetailscreen.components.CommentItem
import kotlinx.coroutines.flow.collectLatest

@Composable
fun PostDetailScreen(
    scaffoldState: ScaffoldState,
    onNavigate: (String) -> Unit = {},
    shouldShowKeyboard: Boolean = false,
    viewModel: PostDetailViewModel = hiltViewModel()
) {
    val state = viewModel.state.value
    val commentTextFieldState = viewModel.commentTextState.value
    val context = LocalContext.current
    val focusRequester = remember { FocusRequester() }

    LaunchedEffect(key1 = true) {
        viewModel.eventFlow.collectLatest {
            when (it) {
                is UiEvent.SnackBarEvent -> {
                    scaffoldState.snackbarHostState.showSnackbar(
                        message = it.uiText.asString(context)
                    )
                }
                else -> Unit
            }
        }
    }

    LaunchedEffect(key1 = true) {
        if (shouldShowKeyboard) {
            context.showKeyboard()
            focusRequester.requestFocus()
        }
    }

    Surface(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.onSurface)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(SpaceSmall)
        ) {
            StandardTopBar(
                title = {
                    Text(
                        text = stringResource(R.string.home),
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colors.onBackground
                    )
                },
                modifier = Modifier.fillMaxWidth()
            )

            LazyColumn(
                modifier = Modifier
                    .weight(1f)
            ) {
                item {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(SpaceSmall)
                    ) {
                        state.post?.let { it ->
                            ActionRow(
                                post = it,
                                imageSize = ProfilePictureSizeSmall,
                                modifier = Modifier.fillMaxWidth(),
                                onUserClick = onNavigate
                            )

                            Column(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .clip(MaterialTheme.shapes.medium)
                            ) {
                                Text(
                                    text = it.description,
                                    style = MaterialTheme.typography.body1,
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colors.onSurface
                                )

                                Spacer(modifier = Modifier.height(SpaceMedium))

                                ContentLoader(contentUrl = it.contentUrl)

                                Spacer(modifier = Modifier.height(SpaceMedium))

                                InteractiveButtons(
                                    post = it,
                                    isLiked = state.post.isLiked,
                                    onNavigate = onNavigate,
                                    onLikeClick = {
                                        viewModel.onEvent(PostDetailEvent.LikePost)
                                    },
                                    onCommentClick = {
                                        context.showKeyboard()
                                        focusRequester.requestFocus()
                                    },
                                    onShareClick = {
                                        context.sharePost(postId = it.id)
                                    }
                                )

                                Spacer(modifier = Modifier.height(SpaceSmall))
                            }
                        }
                    }
                }

                items(state.comments) { comment ->
                    CommentItem(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(
                                horizontal = SpaceLarge,
                                vertical = SpaceSmall
                            ),
                        comment = comment,
                        onUserClick = onNavigate,
                        onLikeClick = {
                            viewModel.onEvent(PostDetailEvent.LikeComment(commentId = comment.id))
                        }
                    )
                }
            }

            StandardTextField(
                text = commentTextFieldState.text,
                onValueChange = {
                    viewModel.onEvent(PostDetailEvent.EnteredComment(it))
                },
                modifier = Modifier
                    .padding(SpaceMedium)
                    .focusRequester(focusRequester = focusRequester)
                    .clip(MaterialTheme.shapes.medium)
                    .background(color = MaterialTheme.colors.background),
                hint = stringResource(id = R.string.comment),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    imeAction = Done
                ),
                trailingIcon = {
                    IconButton(
                        onClick = {
                            viewModel.onEvent(PostDetailEvent.Comment)
                        },
                        enabled = commentTextFieldState.error == null
                    ) {
                        Icon(
                            imageVector = Icons.Default.Send,
                            tint = if (commentTextFieldState.error == null) {
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
    }
}
