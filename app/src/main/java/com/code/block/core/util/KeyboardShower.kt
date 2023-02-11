package com.code.block.core.util

import android.content.Context
import android.view.inputmethod.InputMethodManager

object KeyboardShower {
    @Suppress("DEPRECATION")
    fun Context.showKeyboard() {
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager?
        imm?.showSoftInput(null, InputMethodManager.SHOW_FORCED)
    }
}
