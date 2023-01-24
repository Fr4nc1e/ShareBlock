package com.code.block.feature.post.presentation.postdetailscreen

import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.* // ktlint-disable no-wildcard-imports
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.* // ktlint-disable no-wildcard-imports
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Send
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.code.block.R
import com.code.block.core.presentation.components.ActionRow
import com.code.block.core.presentation.components.InteractiveButtons
import com.code.block.core.presentation.components.StandardTextField
import com.code.block.core.presentation.components.StandardTopBar
import com.code.block.core.presentation.ui.theme.ProfilePictureSizeSmall
import com.code.block.core.presentation.ui.theme.SpaceLarge
import com.code.block.core.presentation.ui.theme.SpaceMedium
import com.code.block.core.presentation.ui.theme.SpaceSmall
import com.code.block.core.util.UiEvent
import com.code.block.core.util.VideoPlayer
import com.code.block.core.util.asString
import com.code.block.feature.post.presentation.postdetailscreen.components.CommentItem
import kotlinx.coroutines.flow.collectLatest

@Composable
fun PostDetailScreen(
    scaffoldState: ScaffoldState,
    onNavigate: (String) -> Unit = {},
    viewModel: PostDetailViewModel = hiltViewModel()
) {
    val state = viewModel.state.value
    val commentTextFieldState = viewModel.commentTextState.value
    val context = LocalContext.current

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

    Column(
        modifier = Modifier.fillMaxSize()
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
                .background(MaterialTheme.colors.background)
        ) {
            item {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(SpaceSmall)
                        .background(MaterialTheme.colors.background)
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
                                .shadow(5.dp)
                                .background(MaterialTheme.colors.background)
                        ) {
                            Text(
                                text = it.description,
                                style = MaterialTheme.typography.body1
                            )

                            Spacer(modifier = Modifier.height(SpaceMedium))

                            if (it.contentUrl.takeLastWhile { it != '.' } != "mp4") {
                                Image(
                                    painter = rememberAsyncImagePainter(
                                        ImageRequest.Builder(LocalContext.current)
                                            .data(data = it.contentUrl)
                                            .apply(
                                                block = fun ImageRequest.Builder.() {
                                                    crossfade(true)
                                                }
                                            ).build()
                                    ),
                                    contentDescription = null,
                                    contentScale = ContentScale.Crop,
                                    modifier = Modifier
                                        .padding(horizontal = SpaceSmall)
                                        .fillMaxWidth()
                                        .aspectRatio(16f / 9f)
                                        .clip(RoundedCornerShape(10.dp))
                                )
                            } else { VideoPlayer(uri = Uri.parse(it.contentUrl)) }

                            Spacer(modifier = Modifier.height(SpaceMedium))

                            Divider(
                                color = MaterialTheme.colors.surface,
                                thickness = 2.dp
                            )

                            Spacer(modifier = Modifier.height(SpaceSmall))

                            InteractiveButtons(
                                post = it,
                                onNavigate = onNavigate,
                                isLiked = state.post.isLiked,
                                onLikeClick = {
                                    viewModel.onEvent(PostDetailEvent.LikePost)
                                }
                            )

                            Spacer(modifier = Modifier.height(SpaceSmall))

                            Divider(
                                color = MaterialTheme.colors.surface,
                                thickness = 2.dp
                            )
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
        Row(
            modifier = Modifier
                .padding(SpaceSmall),
            verticalAlignment = CenterVertically
        ) {
            StandardTextField(
                text = commentTextFieldState.text,
                onValueChange = {
                    viewModel.onEvent(PostDetailEvent.EnteredComment(it))
                },
                modifier = Modifier
                    .clip(MaterialTheme.shapes.medium)
                    .background(color = MaterialTheme.colors.background),
                hint = stringResource(id = R.string.comment),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Done
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
                            } else MaterialTheme.colors.background,
                            contentDescription = stringResource(id = R.string.send_comment)
                        )
                    }
                }
            )
            if (viewModel.commentState.value.isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier.align(CenterVertically),
                    strokeWidth = 2.dp
                )
            }
        }
    }
}
