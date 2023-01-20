package com.code.block.core.util

import java.text.SimpleDateFormat
import java.util.*

object DateFormattedUtil {
    fun timestampToFormattedString(timestamp: Long, pattern: String): String {
        return SimpleDateFormat(pattern, Locale.getDefault()).run {
            format(timestamp)
        }
    }
}
