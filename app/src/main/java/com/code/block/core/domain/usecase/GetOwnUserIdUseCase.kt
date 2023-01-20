package com.code.block.core.domain.usecase

import android.content.SharedPreferences
import com.code.block.core.utils.Constants

class GetOwnUserIdUseCase(
    private val sharedPreferences: SharedPreferences
) {
    operator fun invoke(): String {
        return (sharedPreferences.getString(Constants.KEY_USER_ID, "") ?: "")
    }
}
