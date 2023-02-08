package com.code.block.feature.profile.presentation.editprofilescreen.components

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.outlined.Close
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.hilt.navigation.compose.hiltViewModel
import com.code.block.R
import com.code.block.core.presentation.components.StandardTextField
import com.code.block.core.presentation.ui.theme.IconSizeMedium
import com.code.block.core.presentation.ui.theme.IconSizeSmall
import com.code.block.core.presentation.ui.theme.SpaceSmall
import com.code.block.feature.profile.domain.error.EditProfileError
import com.code.block.feature.profile.presentation.editprofilescreen.EditProfileEvent
import com.code.block.feature.profile.presentation.editprofilescreen.EditProfileViewModel

@Composable
fun EditTextSection(
    viewModel: EditProfileViewModel = hiltViewModel(),
) {
    val usernameState = viewModel.usernameState.value
    val qqTextState = viewModel.qqTextFieldState.value
    val weChatTextState = viewModel.weChatTextFieldState.value
    val gitHubTextState = viewModel.githubTextFieldState.value
    val bioState = viewModel.bioState.value

    StandardTextField(
        modifier = Modifier
            .fillMaxWidth(),
        text = usernameState.text,
        hint = stringResource(id = R.string.username_hint),
        label = {
            Text(
                text = stringResource(id = R.string.username_label),
                color = MaterialTheme.colors.onSurface,
            )
        },
        error = when (viewModel.usernameState.value.error) {
            is EditProfileError.FieldEmpty -> stringResource(id = R.string.this_field_cant_be_empty)
            else -> ""
        },
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Person,
                contentDescription = stringResource(R.string.user_icon),
                tint = MaterialTheme.colors.onBackground,
                modifier = Modifier.size(IconSizeMedium),
            )
        },
        trailingIcon = {
            if (usernameState.text.isNotEmpty()) {
                IconButton(
                    onClick = {
                        viewModel.onEvent(EditProfileEvent.ClearUsername)
                    },
                ) {
                    Icon(
                        imageVector = Icons.Outlined.Close,
                        contentDescription = stringResource(id = R.string.clear_text),
                        tint = MaterialTheme.colors.onBackground,
                        modifier = Modifier.size(IconSizeMedium),
                    )
                }
            }
        },
        onValueChange = {
            viewModel.onEvent(EditProfileEvent.EnteredUsername(it))
        },
    )

    Spacer(modifier = Modifier.height(SpaceSmall))

    StandardTextField(
        modifier = Modifier
            .fillMaxWidth(),
        text = qqTextState.text,
        hint = stringResource(id = R.string.qq_hint),
        label = {
            Text(
                text = stringResource(id = R.string.qq_label),
                color = MaterialTheme.colors.onSurface,
            )
        },
        error = when (qqTextState.error) {
            is EditProfileError.FieldEmpty -> {
                stringResource(id = R.string.this_field_cant_be_empty)
            }
            else -> ""
        },
        leadingIcon = {
            Icon(
                painter = painterResource(id = R.drawable.qq_penguin_shape),
                contentDescription = stringResource(R.string.qq_icon),
                tint = MaterialTheme.colors.onBackground,
                modifier = Modifier.size(IconSizeSmall),
            )
        },
        trailingIcon = {
            if (qqTextState.text.isNotEmpty()) {
                IconButton(
                    onClick = {
                        viewModel.onEvent(EditProfileEvent.ClearQq)
                    },
                ) {
                    Icon(
                        imageVector = Icons.Outlined.Close,
                        contentDescription = stringResource(id = R.string.clear_text),
                        tint = MaterialTheme.colors.onBackground,
                        modifier = Modifier.size(IconSizeMedium),
                    )
                }
            }
        },
        onValueChange = {
            viewModel.onEvent(EditProfileEvent.EnteredQq(it))
        },
    )

    Spacer(modifier = Modifier.height(SpaceSmall))

    StandardTextField(
        modifier = Modifier
            .fillMaxWidth(),
        text = weChatTextState.text,
        hint = stringResource(id = R.string.wechat_hint),
        label = {
            Text(
                text = stringResource(id = R.string.wechat_label),
                color = MaterialTheme.colors.onSurface,
            )
        },
        error = when (weChatTextState.error) {
            is EditProfileError.FieldEmpty -> {
                stringResource(id = R.string.this_field_cant_be_empty)
            }
            else -> ""
        },
        leadingIcon = {
            Icon(
                painter = painterResource(id = R.drawable.wechat),
                contentDescription = stringResource(R.string.wechat_icon),
                tint = MaterialTheme.colors.onBackground,
                modifier = Modifier.size(IconSizeSmall),
            )
        },
        trailingIcon = {
            if (weChatTextState.text.isNotEmpty()) {
                IconButton(
                    onClick = {
                        viewModel.onEvent(EditProfileEvent.ClearWeChat)
                    },
                ) {
                    Icon(
                        imageVector = Icons.Outlined.Close,
                        contentDescription = stringResource(id = R.string.clear_text),
                        tint = MaterialTheme.colors.onBackground,
                        modifier = Modifier.size(IconSizeMedium),
                    )
                }
            }
        },
        onValueChange = {
            viewModel.onEvent(EditProfileEvent.EnteredWeChat(it))
        },
    )

    Spacer(modifier = Modifier.height(SpaceSmall))

    StandardTextField(
        modifier = Modifier
            .fillMaxWidth(),
        text = gitHubTextState.text,
        hint = stringResource(id = R.string.github_hint),
        label = {
            Text(
                text = stringResource(id = R.string.github_label),
                color = MaterialTheme.colors.onSurface,
            )
        },
        error = when (gitHubTextState.error) {
            is EditProfileError.FieldEmpty -> {
                stringResource(id = R.string.this_field_cant_be_empty)
            }
            else -> ""
        },
        leadingIcon = {
            Icon(
                painter = painterResource(id = R.drawable.github),
                contentDescription = stringResource(R.string.github_icon),
                tint = MaterialTheme.colors.onBackground,
                modifier = Modifier.size(IconSizeSmall),
            )
        },
        trailingIcon = {
            if (gitHubTextState.text.isNotEmpty()) {
                IconButton(
                    onClick = {
                        viewModel.onEvent(EditProfileEvent.ClearGitHub)
                    },
                ) {
                    Icon(
                        imageVector = Icons.Outlined.Close,
                        contentDescription = stringResource(id = R.string.clear_text),
                        tint = MaterialTheme.colors.onBackground,
                        modifier = Modifier.size(IconSizeMedium),
                    )
                }
            }
        },
        onValueChange = {
            viewModel.onEvent(EditProfileEvent.EnteredGitHub(it))
        },
    )

    Spacer(modifier = Modifier.height(SpaceSmall))

    StandardTextField(
        modifier = Modifier
            .fillMaxWidth(),
        text = bioState.text,
        maxLength = 96,
        maxLines = 3,
        singleLine = false,
        keyboardOptions = KeyboardOptions(
            imeAction = ImeAction.Done,
        ),
        hint = stringResource(id = R.string.bio_hint),
        label = {
            Text(
                text = stringResource(id = R.string.bio_label),
                color = MaterialTheme.colors.onSurface,
            )
        },
        error = when (bioState.error) {
            is EditProfileError.FieldEmpty -> {
                stringResource(id = R.string.this_field_cant_be_empty)
            }
            else -> ""
        },
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Description,
                contentDescription = stringResource(R.string.bio_icon),
                tint = MaterialTheme.colors.onBackground,
                modifier = Modifier.size(IconSizeMedium),
            )
        },
        trailingIcon = {
            if (bioState.text.isNotEmpty()) {
                IconButton(
                    onClick = {
                        viewModel.onEvent(EditProfileEvent.ClearBio)
                    },
                ) {
                    Icon(
                        imageVector = Icons.Outlined.Close,
                        contentDescription = stringResource(id = R.string.clear_text),
                        tint = MaterialTheme.colors.onBackground,
                        modifier = Modifier.size(IconSizeMedium),
                    )
                }
            }
        },
        onValueChange = {
            viewModel.onEvent(EditProfileEvent.EnteredBio(it))
        },
    )
}
