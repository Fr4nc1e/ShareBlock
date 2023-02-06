package com.code.block.feature.post.presentation.createpostscreen

import android.Manifest
import android.graphics.Color
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.launch
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.* // ktlint-disable no-wildcard-imports
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.* // ktlint-disable no-wildcard-imports
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Send
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment.Companion.BottomEnd
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.canhub.cropper.CropImageContract
import com.canhub.cropper.CropImageContractOptions
import com.canhub.cropper.CropImageOptions
import com.code.block.R
import com.code.block.core.presentation.components.StandardTopBar
import com.code.block.core.presentation.ui.theme.SpaceLarge
import com.code.block.core.presentation.ui.theme.SpaceMedium
import com.code.block.core.presentation.ui.theme.SpaceSmall
import com.code.block.core.presentation.ui.theme.quicksand
import com.code.block.core.util.BitMapTransformer
import com.code.block.core.util.ContentPreviewer
import com.code.block.core.util.ui.UiEvent
import com.code.block.core.util.ui.asString
import com.code.block.core.util.ui.multilfab.MultiFabItem
import com.code.block.core.util.ui.multilfab.MultiFloatingActionButton
import com.code.block.core.util.ui.multilfab.fabItems
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@Composable
fun CreatePostScreen(
    onNavigateUp: () -> Unit = {},
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
                viewModel.onEvent(CreatePostEvent.InputContent(it.uriContent))
            }
        )
    val imageLauncher =
        rememberLauncherForActivityResult(
            contract = ActivityResultContracts.PickVisualMedia(),
            onResult = {
                val cropOptions = CropImageContractOptions(
                    uri = it,
                    cropImageOptions = CropImageOptions().apply {
                        showIntentChooser = true
                        activityBackgroundColor = Color.rgb(0, 0, 0)
                    }
                )
                cropActivityLauncher.launch(cropOptions)
            }
        )
    val videoLauncher =
        rememberLauncherForActivityResult(
            contract = ActivityResultContracts.PickVisualMedia(),
            onResult = {
                viewModel.onEvent(CreatePostEvent.InputContent(it))
            }
        )
    val cameraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicturePreview(),
        onResult = {
            it?.let {
                val cropOptions = CropImageContractOptions(
                    uri = BitMapTransformer.getImageUriFromBitmap(
                        context = context,
                        bitmap = it
                    ).also { uri -> println(uri) },
                    cropImageOptions = CropImageOptions().apply {
                        showIntentChooser = true
                        activityBackgroundColor = Color.rgb(0, 0, 0)
                    }
                )
                cropActivityLauncher.launch(cropOptions)
            }
        }
    )
    val cameraPermissionResultLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = { isGranted ->
            if (isGranted) {
                cameraLauncher.launch()
            } else {
                scope.launch {
                    scaffoldState.snackbarHostState.showSnackbar(
                        message = "No camera permission."
                    )
                }
            }
        }
    )

    LaunchedEffect(
        key1 = true,
        block = {
            viewModel.eventFlow.collectLatest {
                when (it) {
                    is UiEvent.NavigateUp -> onNavigateUp()
                    is UiEvent.SnackBarEvent -> scope.launch {
                        scaffoldState.snackbarHostState.showSnackbar(
                            message = it.uiText.asString(context)
                        )
                    }
                    else -> Unit
                }
            }
        }
    )

    Surface(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.surface)
            .padding(SpaceSmall)
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            MultiFloatingActionButton(
                srcIcon = Icons.Filled.Add,
                modifier = Modifier.align(BottomEnd)
                    .padding(bottom = SpaceMedium),
                items = fabItems,
                onFabItemClicked = { item: MultiFabItem ->
                    when (item.label) {
                        "photo" -> {
                            imageLauncher.launch(
                                PickVisualMediaRequest(
                                    ActivityResultContracts.PickVisualMedia.ImageOnly
                                )
                            )
                        }
                        "video" -> {
                            videoLauncher.launch(
                                PickVisualMediaRequest(
                                    ActivityResultContracts.PickVisualMedia.VideoOnly
                                )
                            )
                        }
                        "camera" -> {
                            cameraPermissionResultLauncher.launch(Manifest.permission.CAMERA)
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
                    navActions = {
                        Button(
                            onClick = {
                                viewModel.onEvent(CreatePostEvent.Post)
                            },
                            shape = RoundedCornerShape(12.dp),
                            colors = when (viewModel.isLoading.value) {
                                true -> ButtonDefaults.buttonColors(
                                    backgroundColor = MaterialTheme.colors.background,
                                    contentColor = MaterialTheme.colors.onBackground
                                )
                                false -> ButtonDefaults.buttonColors(
                                    backgroundColor = MaterialTheme.colors.primary,
                                    contentColor = MaterialTheme.colors.onPrimary
                                )
                            },
                            enabled = !viewModel.isLoading.value
                        ) {
                            Text(
                                text = stringResource(id = R.string.post)
                            )
                            Spacer(modifier = Modifier.width(SpaceSmall))
                            if (viewModel.isLoading.value) {
                                CircularProgressIndicator(
                                    modifier = Modifier
                                        .size(20.dp)
                                        .align(CenterVertically)
                                )
                            } else {
                                Icon(
                                    imageVector = Icons.Default.Send,
                                    contentDescription = null
                                )
                            }
                        }
                    },
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = viewModel.descriptionState.value.text,
                    onValueChange = {
                        if (it.length < 32 * 5) {
                            viewModel.onEvent(CreatePostEvent.EnteredDescription(it))
                        }
                    },
                    placeholder = {
                        Text(
                            text = stringResource(R.string.description_hint),
                            style = MaterialTheme.typography.body1,
                            color = MaterialTheme.colors.onSurface
                        )
                    },
                    textStyle = TextStyle(
                        color = MaterialTheme.colors.onSurface,
                        fontWeight = FontWeight.Bold,
                        fontFamily = quicksand,
                        fontSize = 20.sp
                    ),
                    maxLines = 5,
                    singleLine = false,
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Done
                    ),
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        backgroundColor = MaterialTheme.colors.background,
                        textColor = MaterialTheme.colors.onBackground,
                        focusedBorderColor = MaterialTheme.colors.background,
                        unfocusedBorderColor = MaterialTheme.colors.background
                    ),
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(SpaceLarge))

                ContentPreviewer(contentUri = contentUri)
            }
        }
    }
}
