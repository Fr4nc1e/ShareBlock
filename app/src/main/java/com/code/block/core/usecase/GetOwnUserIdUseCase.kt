package com.code.block.core.usecase

import android.content.SharedPreferences
import com.code.block.core.util.Constants

class GetOwnUserIdUseCase(
    private val sharedPreferences: SharedPreferences
) {
    operator fun invoke(): String {
        return sharedPreferences.getString(Constants.KEY_USER_ID, "") ?: ""
    }
}
