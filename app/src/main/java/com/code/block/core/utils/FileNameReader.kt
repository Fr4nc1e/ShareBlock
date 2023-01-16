package com.code.block.core.utils

import android.content.ContentResolver
import android.net.Uri
import android.provider.OpenableColumns

object FileNameReader {
    fun ContentResolver.getFileName(
        uri: Uri
    ): String {
        val returnCursor = query(
            uri,
            null,
            null,
            null,
            null
        ) ?: return ""
        val nameIndex = returnCursor.getColumnIndex(OpenableColumns.DISPLAY_NAME)
        returnCursor.moveToFirst()
        val fileName = returnCursor.getString(nameIndex)
        returnCursor.close()
        return fileName
    }
}
