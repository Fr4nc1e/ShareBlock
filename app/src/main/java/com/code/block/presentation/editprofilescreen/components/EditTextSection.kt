package com.code.block.presentation.editprofilescreen.components

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
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
import androidx.hilt.navigation.compose.hiltViewModel
import com.code.block.R
import com.code.block.presentation.components.StandardTextField
import com.code.block.presentation.editprofilescreen.EditProfileEvent
import com.code.block.presentation.editprofilescreen.EditProfileState
import com.code.block.presentation.editprofilescreen.EditProfileViewModel
import com.code.block.ui.theme.IconSizeMedium
import com.code.block.ui.theme.IconSizeSmall
import com.code.block.ui.theme.SpaceSmall

@Composable
fun EditTextSection(
    viewModel: EditProfileViewModel = hiltViewModel()
) {
    val state = viewModel.state

    StandardTextField(
        modifier = Modifier
            .fillMaxWidth(),
        text = state.value.usernameText,
        hint = stringResource(id = R.string.username_hint),
        label = {
            Text(text = stringResource(id = R.string.username_label))
        },
        error = when (state.value.usernameError) {
            is EditProfileState.UsernameError.FieldEmpty -> {
                stringResource(id = R.string.this_field_cant_be_empty)
            }
            is EditProfileState.UsernameError.InputTooShort -> {
                stringResource(id = R.string.username_too_short)
            }
            else -> ""
        },
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Person,
                contentDescription = stringResource(R.string.user_icon),
                tint = MaterialTheme.colors.onBackground,
                modifier = Modifier.size(IconSizeMedium)
            )
        },
        trailingIcon = {
            if (state.value.usernameText.isNotEmpty()) {
                IconButton(
                    onClick = {
                        viewModel.onEvent(EditProfileEvent.ClearUsername)
                    }
                ) {
                    Icon(
                        imageVector = Icons.Outlined.Close,
                        contentDescription = stringResource(id = R.string.clear_text),
                        tint = MaterialTheme.colors.onBackground,
                        modifier = Modifier.size(IconSizeMedium)
                    )
                }
            }
        },
        onValueChange = {
            viewModel.onEvent(EditProfileEvent.EnteredUsername(it))
        }
    )

    Spacer(modifier = Modifier.height(SpaceSmall))

    StandardTextField(
        modifier = Modifier
            .fillMaxWidth(),
        text = state.value.qqText,
        hint = stringResource(id = R.string.qq_hint),
        label = {
            Text(text = stringResource(id = R.string.qq_label))
        },
        error = when (state.value.qqError) {
            is EditProfileState.QqError.FieldEmpty -> {
                stringResource(id = R.string.this_field_cant_be_empty)
            }
            else -> ""
        },
        leadingIcon = {
            Icon(
                painter = painterResource(id = R.drawable.qq_penguin_shape),
                contentDescription = stringResource(R.string.qq_icon),
                tint = MaterialTheme.colors.onBackground,
                modifier = Modifier.size(IconSizeSmall)
            )
        },
        trailingIcon = {
            if (state.value.qqText.isNotEmpty()) {
                IconButton(
                    onClick = {
                        viewModel.onEvent(EditProfileEvent.ClearQq)
                    }
                ) {
                    Icon(
                        imageVector = Icons.Outlined.Close,
                        contentDescription = stringResource(id = R.string.clear_text),
                        tint = MaterialTheme.colors.onBackground,
                        modifier = Modifier.size(IconSizeMedium)
                    )
                }
            }
        },
        onValueChange = {
            viewModel.onEvent(EditProfileEvent.EnteredQq(it))
        }
    )

    Spacer(modifier = Modifier.height(SpaceSmall))

    StandardTextField(
        modifier = Modifier
            .fillMaxWidth(),
        text = state.value.weChatText,
        hint = stringResource(id = R.string.wechat_hint),
        label = {
            Text(text = stringResource(id = R.string.wechat_label))
        },
        error = when (state.value.weChatError) {
            is EditProfileState.WeChatError.FieldEmpty -> {
                stringResource(id = R.string.this_field_cant_be_empty)
            }
            else -> ""
        },
        leadingIcon = {
            Icon(
                painter = painterResource(id = R.drawable.wechat),
                contentDescription = stringResource(R.string.wechat_icon),
                tint = MaterialTheme.colors.onBackground,
                modifier = Modifier.size(IconSizeSmall)
            )
        },
        trailingIcon = {
            if (state.value.weChatText.isNotEmpty()) {
                IconButton(
                    onClick = {
                        viewModel.onEvent(EditProfileEvent.ClearWeChat)
                    }
                ) {
                    Icon(
                        imageVector = Icons.Outlined.Close,
                        contentDescription = stringResource(id = R.string.clear_text),
                        tint = MaterialTheme.colors.onBackground,
                        modifier = Modifier.size(IconSizeMedium)
                    )
                }
            }
        },
        onValueChange = {
            viewModel.onEvent(EditProfileEvent.EnteredWeChat(it))
        }
    )

    Spacer(modifier = Modifier.height(SpaceSmall))

    StandardTextField(
        modifier = Modifier
            .fillMaxWidth(),
        text = state.value.gitHubText,
        hint = stringResource(id = R.string.github_hint),
        label = {
            Text(text = stringResource(id = R.string.github_label))
        },
        error = when (state.value.gitHubError) {
            is EditProfileState.GitHubError.FieldEmpty -> {
                stringResource(id = R.string.this_field_cant_be_empty)
            }
            else -> ""
        },
        leadingIcon = {
            Icon(
                painter = painterResource(id = R.drawable.github),
                contentDescription = stringResource(R.string.github_icon),
                tint = MaterialTheme.colors.onBackground,
                modifier = Modifier.size(IconSizeSmall)
            )
        },
        trailingIcon = {
            if (state.value.gitHubText.isNotEmpty()) {
                IconButton(
                    onClick = {
                        viewModel.onEvent(EditProfileEvent.ClearGitHub)
                    }
                ) {
                    Icon(
                        imageVector = Icons.Outlined.Close,
                        contentDescription = stringResource(id = R.string.clear_text),
                        tint = MaterialTheme.colors.onBackground,
                        modifier = Modifier.size(IconSizeMedium)
                    )
                }
            }
        },
        onValueChange = {
            viewModel.onEvent(EditProfileEvent.EnteredGitHub(it))
        }
    )

    Spacer(modifier = Modifier.height(SpaceSmall))

    StandardTextField(
        modifier = Modifier
            .fillMaxWidth(),
        text = state.value.bioText,
        maxLength = 96,
        maxLines = 3,
        singleLine = false,
        hint = stringResource(id = R.string.bio_hint),
        label = {
            Text(text = stringResource(id = R.string.bio_label))
        },
        error = when (state.value.bioError) {
            is EditProfileState.BioError.FieldEmpty -> {
                stringResource(id = R.string.this_field_cant_be_empty)
            }
            else -> ""
        },
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Description,
                contentDescription = stringResource(R.string.bio_icon),
                tint = MaterialTheme.colors.onBackground,
                modifier = Modifier.size(IconSizeMedium)
            )
        },
        trailingIcon = {
            if (state.value.bioText.isNotEmpty()) {
                IconButton(
                    onClick = {
                        viewModel.onEvent(EditProfileEvent.ClearBio)
                    }
                ) {
                    Icon(
                        imageVector = Icons.Outlined.Close,
                        contentDescription = stringResource(id = R.string.clear_text),
                        tint = MaterialTheme.colors.onBackground,
                        modifier = Modifier.size(IconSizeMedium)
                    )
                }
            }
        },
        onValueChange = {
            viewModel.onEvent(EditProfileEvent.EnteredBio(it))
        }
    )
}
