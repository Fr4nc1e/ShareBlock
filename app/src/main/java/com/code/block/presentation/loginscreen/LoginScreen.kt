package com.code.block.presentation.loginscreen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.* // ktlint-disable no-wildcard-imports
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.code.block.R
import com.code.block.presentation.components.StandardTextField
import com.code.block.presentation.destinations.LoginScreenDestination
import com.code.block.presentation.destinations.MainFeedScreenDestination
import com.code.block.presentation.destinations.RegisterScreenDestination
import com.code.block.ui.theme.IconSizeMedium
import com.code.block.ui.theme.SpaceLarge
import com.code.block.ui.theme.SpaceMedium
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Destination
@Composable
fun LoginScreen(
    navigator: DestinationsNavigator,
    viewModel: LoginViewModel = hiltViewModel()
) {
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

            // Username/E-mail Input
            StandardTextField(
                text = viewModel.state.usernameText,
                onValueChange = {
                    viewModel.setUsernameText(it)
                },
                hint = stringResource(id = R.string.login_hint),
                label = {
                    Text(
                        text = "Username/E-mail",
                        style = MaterialTheme.typography.body1
                    )
                },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Email,
                        contentDescription = stringResource(R.string.email_icon),
                        tint = MaterialTheme.colors.onBackground,
                        modifier = Modifier.size(IconSizeMedium)
                    )
                },
                keyboardType = KeyboardType.Email,
                error = viewModel.state.usernameError
            )

            Spacer(modifier = Modifier.height(SpaceMedium))

            // Password Input
            StandardTextField(
                text = viewModel.state.passwordText,
                onValueChange = {
                    viewModel.setPasswordText(it)
                },
                hint = stringResource(id = R.string.password_hint),
                label = {
                    Text(
                        text = "Password",
                        style = MaterialTheme.typography.body1
                    )
                },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Lock,
                        contentDescription = stringResource(R.string.password_icon),
                        tint = MaterialTheme.colors.onBackground,
                        modifier = Modifier.size(IconSizeMedium)
                    )
                },
                keyboardType = KeyboardType.Password,
                error = viewModel.state.passwordError,
                isPasswordVisible = viewModel.state.showPassword,
                onPasswordToggleClick = {
                    viewModel.setShowPassword(it)
                }
            )

            Spacer(modifier = Modifier.height(SpaceMedium))

            Button(
                modifier = Modifier
                    .align(Alignment.End),
                onClick = {
                    navigator.navigate(MainFeedScreenDestination) {
                        popUpTo(LoginScreenDestination.route) {
                            inclusive = true
                        }
                    }
                }
            ) {
                Text(
                    text = stringResource(id = R.string.login),
                    color = MaterialTheme.colors.onPrimary
                )
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
                    navigator.navigate(RegisterScreenDestination)
                }
        )
    }
}
