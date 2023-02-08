package com.code.block.core.presentation.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.* // ktlint-disable no-wildcard-imports
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.code.block.R
import com.code.block.core.util.Constants

@Composable
fun StandardTextField(
    modifier: Modifier = Modifier,
    text: String = "",
    hint: String = "",
    label: @Composable (() -> Unit)? = null,
    maxLength: Int = 32,
    error: String = "",
    style: TextStyle = TextStyle(color = MaterialTheme.colors.onBackground),
    singleLine: Boolean = true,
    maxLines: Int = 1,
    focusManager: FocusManager = LocalFocusManager.current,
    leadingIcon: @Composable (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = null,
    keyboardType: KeyboardType = KeyboardType.Text,
    keyboardOptions: KeyboardOptions = KeyboardOptions(
        keyboardType = keyboardType,
        imeAction = if (keyboardType == KeyboardType.Password) {
            ImeAction.Done
        } else {
            ImeAction.Next
        },
    ),
    keyboardActions: KeyboardActions = KeyboardActions(
        onNext = {
            focusManager.moveFocus(FocusDirection.Down)
        },
        onSearch = {
            focusManager.clearFocus()
        },
        onDone = {
            focusManager.clearFocus()
        },
    ),
    isRegisterPage: Boolean = Constants.LOGIN_PAGE,
    isPasswordToggleDisplayed: Boolean = keyboardType == KeyboardType.Password,
    isPasswordVisible: Boolean = false,
    onPasswordToggleClick: (Boolean) -> Unit = {},
    onValueChange: (String) -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth(),
    ) {
        OutlinedTextField(
            value = text,
            onValueChange = {
                if (it.length <= maxLength) {
                    onValueChange(it)
                }
            },
            label = label,
            placeholder = {
                Text(
                    text = hint,
                    style = MaterialTheme.typography.body1,
                    color = MaterialTheme.colors.onSurface,
                )
            },
            maxLines = maxLines,
            textStyle = style,
            isError = error != "",
            shape = RoundedCornerShape(8.dp),
            keyboardOptions = keyboardOptions,
            keyboardActions = keyboardActions,
            visualTransformation = if (!isPasswordVisible && isPasswordToggleDisplayed) {
                PasswordVisualTransformation()
            } else {
                VisualTransformation.None
            },
            singleLine = singleLine,
            leadingIcon = leadingIcon,
            trailingIcon = if (isPasswordToggleDisplayed && trailingIcon == null) {
                val icon: @Composable () -> Unit = {
                    IconButton(
                        onClick = {
                            onPasswordToggleClick(!isPasswordVisible)
                        },
                    ) {
                        Icon(
                            imageVector = if (isPasswordVisible) {
                                Icons.Filled.Visibility
                            } else {
                                Icons.Filled.VisibilityOff
                            },
                            tint = MaterialTheme.colors.onSurface,
                            contentDescription = if (isPasswordVisible) {
                                stringResource(R.string.password_visible_content_description)
                            } else {
                                stringResource(R.string.password_hidden_content_description)
                            },
                        )
                    }
                }
                icon
            } else {
                trailingIcon
            },
            modifier = modifier.fillMaxWidth(),
        )

        if (isRegisterPage) {
            Text(
                text = "${text.length} / $maxLength",
                textAlign = TextAlign.End,
                color = MaterialTheme.colors.onBackground,
                style = MaterialTheme.typography.body2,
                modifier = Modifier.fillMaxWidth(),
            )
        }

        if (error.isNotEmpty()) {
            Text(
                text = error,
                style = MaterialTheme.typography.body2,
                color = MaterialTheme.colors.error,
                textAlign = TextAlign.Start,
                modifier = Modifier.fillMaxWidth(),
            )
        }
    }
}
