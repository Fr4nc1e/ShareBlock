package com.code.block.feature.auth.presentation.registerscreen

import androidx.compose.foundation.layout.* // ktlint-disable no-wildcard-imports
import androidx.compose.material.* // ktlint-disable no-wildcard-imports
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.outlined.Close
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.code.block.R
import com.code.block.core.presentation.components.StandardTextField
import com.code.block.core.presentation.ui.theme.IconSizeMedium
import com.code.block.core.presentation.ui.theme.SpaceLarge
import com.code.block.core.presentation.ui.theme.SpaceMedium
import com.code.block.core.utils.Constants
import com.code.block.presentation.destinations.MainFeedScreenDestination
import com.code.block.presentation.destinations.RegisterScreenDestination
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Destination
@Composable
fun RegisterScreen(
    navigator: DestinationsNavigator,
    viewModel: RegisterViewModel = hiltViewModel()
) {
    val state = viewModel.state

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(
                start = SpaceLarge,
                end = SpaceLarge,
                top = SpaceLarge,
                bottom = 50.dp
            )
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxSize()
                .align(Alignment.Center)
        ) {
            Text(
                text = stringResource(id = R.string.register),
                style = MaterialTheme.typography.h1
            )

            Spacer(modifier = Modifier.height(SpaceMedium))

            // E-mail input
            StandardTextField(
                text = state.value.emailText,
                onValueChange = {
                    viewModel.onEvent(RegisterEvent.EnteredEmail(it))
                },
                hint = stringResource(id = R.string.email_hint),
                label = {
                    Text(
                        text = stringResource(id = R.string.email_label),
                        style = MaterialTheme.typography.body1
                    )
                },
                error = when (state.value.emailError) {
                    is RegisterState.EmailError.FieldEmpty -> {
                        stringResource(id = R.string.this_field_cant_be_empty)
                    }
                    is RegisterState.EmailError.InvalidEmail -> {
                        stringResource(id = R.string.invalid_email)
                    }
                    else -> ""
                },
                keyboardType = KeyboardType.Email,
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Email,
                        contentDescription = stringResource(R.string.email_icon),
                        tint = MaterialTheme.colors.onBackground,
                        modifier = Modifier.size(IconSizeMedium)
                    )
                },
                trailingIcon = {
                    if (state.value.emailText.isNotEmpty()) {
                        IconButton(
                            onClick = {
                                viewModel.onEvent(RegisterEvent.ClearEmail)
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
                isRegisterPage = Constants.REGISTER_PAGE
            )

            // Username input
            StandardTextField(
                text = state.value.usernameText,
                onValueChange = {
                    viewModel.onEvent(RegisterEvent.EnteredUsername(it))
                },
                hint = stringResource(id = R.string.username_hint),
                label = {
                    Text(text = stringResource(id = R.string.username_label))
                },
                error = when (state.value.usernameError) {
                    is RegisterState.UsernameError.FieldEmpty -> {
                        stringResource(id = R.string.this_field_cant_be_empty)
                    }
                    is RegisterState.UsernameError.InputTooShort -> {
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
                                viewModel.onEvent(RegisterEvent.ClearUsername)
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
                isRegisterPage = Constants.REGISTER_PAGE
            )

            // Password input
            StandardTextField(
                text = state.value.passwordText,
                onValueChange = {
                    viewModel.onEvent(RegisterEvent.EnteredPassword(it))
                },
                hint = stringResource(id = R.string.password_hint),
                label = {
                    Text(
                        text = stringResource(id = R.string.password_label),
                        style = MaterialTheme.typography.body1
                    )
                },
                error = when (state.value.passwordError) {
                    is RegisterState.PasswordError.FieldEmpty -> {
                        stringResource(id = R.string.this_field_cant_be_empty)
                    }
                    is RegisterState.PasswordError.InputTooShort -> {
                        stringResource(id = R.string.password_too_short)
                    }
                    is RegisterState.PasswordError.InvalidPassword -> {
                        stringResource(id = R.string.invalid_password)
                    }
                    else -> ""
                },
                keyboardType = KeyboardType.Password,
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Lock,
                        contentDescription = stringResource(R.string.password_icon),
                        tint = MaterialTheme.colors.onBackground,
                        modifier = Modifier.size(IconSizeMedium)
                    )
                },
                isPasswordVisible = state.value.isPasswordVisible,
                onPasswordToggleClick = {
                    viewModel.onEvent(RegisterEvent.TogglePasswordVisibility)
                },
                isRegisterPage = Constants.REGISTER_PAGE
            )

            Spacer(modifier = Modifier.height(SpaceMedium))

            Button(
                onClick = {
                    viewModel.onEvent(RegisterEvent.Register)
                    if (
                        state.value.emailError == null && state.value.usernameError == null &&
                        state.value.passwordError == null
                    ) {
                        navigator.navigate(MainFeedScreenDestination) {
                            popUpTo(RegisterScreenDestination.route) {
                                inclusive = true
                            }
                        }
                    }
                },
                modifier = Modifier
                    .align(Alignment.End)
            ) {
                Text(
                    text = stringResource(id = R.string.register),
                    color = MaterialTheme.colors.onPrimary
                )
            }
        }
    }
}
