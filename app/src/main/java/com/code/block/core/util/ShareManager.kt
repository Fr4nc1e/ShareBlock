package com.code.block.core.util

import android.content.Context
import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_NEW_TASK
import android.content.Intent.createChooser

object ShareManager {
    fun Context.sharePost(postId: String) {
        val intent = Intent().apply {
            flags = FLAG_ACTIVITY_NEW_TASK
            action = Intent.ACTION_SEND
            putExtra(
                Intent.EXTRA_TEXT,
                "https://block.com/$postId"
            )
            type = "text/plain"
        }
        if (intent.resolveActivity(packageManager) != null) {
            startActivity(
                createChooser(intent, "Choose an app.")
            )
        }
    }
}
