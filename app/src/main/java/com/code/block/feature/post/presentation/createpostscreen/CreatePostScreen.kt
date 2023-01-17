package com.code.block.feature.post.presentation.createpostscreen

import android.webkit.MimeTypeMap
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.* // ktlint-disable no-wildcard-imports
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.* // ktlint-disable no-wildcard-imports
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Photo
import androidx.compose.material.icons.filled.Send
import androidx.compose.material.icons.filled.VideoFile
import androidx.compose.runtime.* // ktlint-disable no-wildcard-imports
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterEnd
import androidx.compose.ui.Alignment.Companion.CenterStart
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.canhub.cropper.CropImageContract
import com.canhub.cropper.CropImageContractOptions
import com.canhub.cropper.CropImageOptions
import com.code.block.R
import com.code.block.core.presentation.components.StandardTextField
import com.code.block.core.presentation.components.StandardTopBar
import com.code.block.core.presentation.ui.theme.SpaceLarge
import com.code.block.core.presentation.ui.theme.SpaceMedium
import com.code.block.core.presentation.ui.theme.SpaceSmall
import com.code.block.core.utils.UiEvent
import com.code.block.core.utils.VideoPlayer
import com.code.block.core.utils.asString
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@Destination
@Composable
fun CreatePostScreen(
    navigator: DestinationsNavigator,
    scaffoldState: ScaffoldState,
    viewModel: CreatePostViewModel = hiltViewModel()
) {
    val contentUri = viewModel.chosenContentUri.value
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val cropActivityLauncher =
        rememberLauncherForActivityResult(
            contract = CropImageContract(),
            onResult = {
                viewModel.onEvent(CreatePostEvent.CropImage(it.uriContent))
            }
        )
    val imageLauncher =
        rememberLauncherForActivityResult(
            contract = ActivityResultContracts.GetContent(),
            onResult = {
                val cropOptions = CropImageContractOptions(it, CropImageOptions())
                cropActivityLauncher.launch(cropOptions)
            }
        )

    val videoLauncher =
        rememberLauncherForActivityResult(
            contract = ActivityResultContracts.GetContent(),
            onResult = {
                viewModel.onEvent(CreatePostEvent.InputContent(it))
            }
        )

    LaunchedEffect(
        key1 = true,
        block = {
            viewModel.eventFlow.collectLatest {
                when (it) {
                    is UiEvent.Navigate -> Unit
                    is UiEvent.NavigateUp -> navigator.navigateUp()
                    is UiEvent.SnackBarEvent -> scope.launch {
                        scaffoldState.snackbarHostState.showSnackbar(
                            message = it.uiText.asString(context)
                        )
                    }
                }
            }
        }
    )

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        StandardTopBar(
            title = {
                Text(
                    text = stringResource(R.string.make_post),
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colors.onBackground
                )
            },
            modifier = Modifier.fillMaxWidth()
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(SpaceLarge)
        ) {
            Box(
                modifier = Modifier
                    .aspectRatio(16f / 9f)
                    .fillMaxWidth()
                    .clip(MaterialTheme.shapes.medium)
                    .border(
                        width = 1.dp,
                        color = MaterialTheme.colors.onBackground,
                        shape = MaterialTheme.shapes.medium
                    )
            ) {
                Icon(
                    imageVector = Icons.Default.Photo,
                    contentDescription = stringResource(R.string.choose_image),
                    tint = MaterialTheme.colors.onBackground,
                    modifier = Modifier
                        .align(CenterStart)
                        .padding(start = SpaceLarge)
                        .clickable {
                            imageLauncher.launch("image/*")
                        }
                )

                Spacer(modifier = Modifier.width(SpaceLarge))

                Icon(
                    imageVector = Icons.Default.VideoFile,
                    contentDescription = stringResource(R.string.choose_video),
                    tint = MaterialTheme.colors.onBackground,
                    modifier = Modifier
                        .align(CenterEnd)
                        .padding(end = SpaceLarge)
                        .clickable {
                            videoLauncher.launch("video/mp4")
                        }
                )

                contentUri?.let { uri ->
                    val fileExtension = MimeTypeMap
                        .getFileExtensionFromUrl(uri.toString())
                    val mimeType = MimeTypeMap
                        .getSingleton()
                        .getMimeTypeFromExtension(fileExtension)

                    if (mimeType != null && !mimeType.contains("video")) {
                        Image(
                            painter = rememberAsyncImagePainter(
                                model = ImageRequest.Builder(LocalContext.current)
                                    .data(uri)
                                    .build()
                            ),
                            contentDescription = null,
                            modifier = Modifier.matchParentSize()
                        )
                    } else {
                        VideoPlayer(uri = contentUri)
                    }
                }
            }

            Spacer(modifier = Modifier.height(SpaceMedium))

            StandardTextField(
                text = viewModel.descriptionState.value.text,
                hint = stringResource(R.string.description_hint),
                singleLine = false,
                maxLength = 32 * 5,
                maxLines = 5,
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Done
                ),
                onValueChange = {
                    viewModel.onEvent(CreatePostEvent.EnteredDescription(it))
                },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(SpaceLarge))

            Button(
                onClick = {
                    viewModel.onEvent(CreatePostEvent.Post)
                },
                enabled = !viewModel.isLoading.value,
                modifier = Modifier.align(Alignment.End)
            ) {
                Text(
                    text = stringResource(id = R.string.post),
                    color = MaterialTheme.colors.onPrimary
                )
                Spacer(modifier = Modifier.width(SpaceSmall))
                if (viewModel.isLoading.value) {
                    CircularProgressIndicator(
                        color = MaterialTheme.colors.onPrimary,
                        modifier = Modifier
                            .size(20.dp)
                            .align(CenterVertically)
                    )
                }
                Icon(
                    imageVector = Icons.Default.Send,
                    contentDescription = null
                )
            }
        }
    }
}
