package com.code.block.core.util

import android.content.ContentValues
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import androidx.annotation.RequiresApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.io.BufferedInputStream
import java.io.ByteArrayOutputStream
import java.io.IOException
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.URL

object BitmapTransformer {
    suspend fun getBitmapFromUrl(inputUrl: String?): Bitmap? {
        return try {
            val url = URL(inputUrl)
            val inputStream: InputStream = withContext(Dispatchers.IO) {
                (url.openConnection() as HttpURLConnection).run {
                    connect()
                    inputStream
                }
            }
            val bufferedInputStream = BufferedInputStream(inputStream)
            BitmapFactory.decodeStream(bufferedInputStream)
        } catch (e: IOException) {
            e.printStackTrace()
            null
        } catch (e: HttpException) {
            e.printStackTrace()
            null
        }
    }

    @Suppress("DEPRECATION")
    fun getImageUriFromBitmap(context: Context, bitmap: Bitmap): Uri {
        val bytes = ByteArrayOutputStream()
        bitmap.compress(
            /* format = */ Bitmap.CompressFormat.JPEG,
            /* quality = */ 100,
            /* stream = */ bytes,
        )
        val path = MediaStore.Images.Media
            .insertImage(
                /* cr = */ context.contentResolver,
                /* source = */ bitmap,
                /* title = */ "Title",
                /* description = */ null,
            )
        return Uri.parse(path.toString())
    }

    @RequiresApi(Build.VERSION_CODES.Q)
    fun saveImageQ(
        bitmap: Bitmap,
        context: Context,
    ): Uri {
        val filename = "IMG_${System.currentTimeMillis()}.jpg"
        val values = ContentValues().apply {
            put(MediaStore.MediaColumns.DISPLAY_NAME, filename)
            put(MediaStore.MediaColumns.MIME_TYPE, "image/jpg")
            put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_PICTURES)
            put(MediaStore.Video.Media.IS_PENDING, 1)
        }

        val resolver = context.contentResolver
        val uri = resolver.insert(
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
            values,
        )

        uri?.let {
            resolver.openOutputStream(uri)?.use { outputStream ->
                bitmap.compress(
                    /* format = */ Bitmap.CompressFormat.JPEG,
                    /* quality = */ 100,
                    /* stream = */ outputStream,
                )
            }

            values.clear()
            values.put(MediaStore.Video.Media.IS_PENDING, 0)
            resolver.update(
                /* uri = */ uri,
                /* values = */ values,
                /* where = */ null,
                /* selectionArgs = */ null,
            )
        } ?: throw RuntimeException("MediaStore failed for some reason")
        return uri
    }
}
