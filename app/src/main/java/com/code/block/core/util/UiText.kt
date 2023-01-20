package com.code.block.core.util

import android.content.Context
import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.code.block.R

sealed class UiText {
    data class CallResponseText(val value: String) : UiText()
    data class StringResource(@StringRes val id: Int) : UiText()

    companion object {
        fun unknownError(): UiText {
            return StringResource(id = R.string.unknown_error)
        }
    }
}

@Composable
fun UiText.asString(): String {
    return when (this) {
        is UiText.CallResponseText -> this.value
        is UiText.StringResource -> stringResource(id = this.id)
    }
}

fun UiText.asString(context: Context): String {
    return when (this) {
        is UiText.CallResponseText -> this.value
        is UiText.StringResource -> context.getString(this.id)
    }
}
