package com.code.block.feature.auth.presentation.loginscreen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.* // ktlint-disable no-wildcard-imports
import androidx.compose.material.* // ktlint-disable no-wildcard-imports
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.outlined.Close
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.code.block.R
import com.code.block.core.presentation.components.Screen
import com.code.block.core.presentation.components.StandardTextField
import com.code.block.core.presentation.ui.theme.IconSizeMedium
import com.code.block.core.presentation.ui.theme.SpaceLarge
import com.code.block.core.presentation.ui.theme.SpaceMedium
import com.code.block.core.util.ui.UiEvent
import com.code.block.core.util.ui.asString
import com.code.block.feature.auth.domain.error.AuthError
import kotlinx.coroutines.flow.collectLatest

@Composable
fun LoginScreen(
    onNavigate: (String) -> Unit = {},
    onLogin: () -> Unit = {},
    scaffoldState: ScaffoldState,
    viewModel: LoginViewModel = hiltViewModel()
) {
    val loginState = viewModel.loginState
    val emailState = viewModel.emailState
    val passwordState = viewModel.passwordState
    val context = LocalContext.current

    LaunchedEffect(key1 = true) {
        viewModel.eventFlow.collectLatest { event ->
            when (event) {
                is UiEvent.SnackBarEvent -> {
                    scaffoldState.snackbarHostState.showSnackbar(
                        message = event.uiText.asString(context)
                    )
                }
                is UiEvent.Navigate -> {
                    onNavigate(event.route)
                }
                is UiEvent.OnLogin -> {
                    onLogin()
                }
                else -> Unit
            }
        }
    }

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
                text = stringResource(R.string.login),
                style = MaterialTheme.typography.h1
            )

            Spacer(modifier = Modifier.height(SpaceMedium))

            // E-mail Input
            StandardTextField(
                text = emailState.value.text,
                onValueChange = {
                    viewModel.onEvent(LoginEvent.EnteredEmail(it))
                },
                hint = stringResource(id = R.string.email_hint),
                label = {
                    Text(
                        text = stringResource(id = R.string.email_label),
                        style = MaterialTheme.typography.body1
                    )
                },
                error = when (emailState.value.error) {
                    is AuthError.FieldEmpty -> {
                        stringResource(id = R.string.this_field_cant_be_empty)
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
                    if (emailState.value.text.isNotEmpty()) {
                        IconButton(
                            onClick = {
                                viewModel.onEvent(LoginEvent.ClearEmail)
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
                }
            )

            Spacer(modifier = Modifier.height(SpaceMedium))

            // Password Input
            StandardTextField(
                text = passwordState.value.text,
                onValueChange = {
                    viewModel.onEvent(LoginEvent.EnteredPassword(it))
                },
                hint = stringResource(id = R.string.password_hint),
                label = {
                    Text(
                        text = stringResource(id = R.string.password_label),
                        style = MaterialTheme.typography.body1
                    )
                },
                error = when (passwordState.value.error) {
                    is AuthError.FieldEmpty -> {
                        stringResource(id = R.string.this_field_cant_be_empty)
                    }
                    is AuthError.InvalidPassword -> {
                        stringResource(id = R.string.login_invalid_password)
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
                isPasswordVisible = passwordState.value.isPasswordVisible,
                onPasswordToggleClick = {
                    viewModel.onEvent(LoginEvent.TogglePasswordVisibility)
                }
            )

            Spacer(modifier = Modifier.height(SpaceMedium))

            Button(
                modifier = Modifier
                    .align(Alignment.End),
                onClick = {
                    viewModel.onEvent(LoginEvent.Login)
                }
            ) {
                Text(
                    text = stringResource(id = R.string.login),
                    color = MaterialTheme.colors.onPrimary
                )
                if (loginState.value.isLoading) {
                    CircularProgressIndicator()
                }
            }
        }
        Text(
            text = buildAnnotatedString {
                append(stringResource(id = R.string.register_an_account))
                append(" ")
                val signUpText = stringResource(id = R.string.sign_up)
                withStyle(
                    style = SpanStyle(
                        color = MaterialTheme.colors.primary
                    )
                ) {
                    append(signUpText)
                }
            },
            style = MaterialTheme.typography.body1,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .clickable {
                    onNavigate(Screen.RegisterScreen.route)
                }
        )
    }
}
