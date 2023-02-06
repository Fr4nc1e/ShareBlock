package com.code.block.core.util

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.provider.MediaStore
import java.io.ByteArrayOutputStream

object BitMapTransformer {
    fun getImageUriFromBitmap(context: Context, bitmap: Bitmap): Uri {
        val bytes = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes)
        val path = MediaStore.Images.Media
            .insertImage(
                context.contentResolver,
                bitmap,
                "Title",
                null
            )
        return Uri.parse(path.toString())
    }
}
