package com.code.block.feature.post.data.repository

import android.annotation.SuppressLint
import android.content.Context
import android.net.Uri
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.code.block.R
import com.code.block.core.domain.model.Post
import com.code.block.core.utils.Constants
import com.code.block.core.utils.CreatePostResource
import com.code.block.core.utils.FileNameReader.getFileName
import com.code.block.core.utils.Resource
import com.code.block.core.utils.UiText
import com.code.block.feature.post.data.source.paging.PostSource
import com.code.block.feature.post.data.source.remote.CreatePostRequest
import com.code.block.feature.post.data.source.remote.PostApi
import com.code.block.feature.post.domain.repository.PostRepository
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import retrofit2.HttpException
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.IOException

class PostRepositoryImpl(
    private val api: PostApi,
    private val gson: Gson,
    private val appContext: Context
) : PostRepository {

    override val posts: Flow<PagingData<Post>>
        get() = Pager(
            config = PagingConfig(
                pageSize = Constants.PAGE_SIZE_POSTS
            ),
            pagingSourceFactory = {
                PostSource(api)
            }
        ).flow

    @SuppressLint("Recycle")
    override suspend fun createPost(
        description: String,
        contentUri: Uri
    ): CreatePostResource {
        val request = CreatePostRequest(description)

        val file = withContext(Dispatchers.IO) {
            appContext.contentResolver.openFileDescriptor(contentUri, "r")?.let { fd ->
                val inputStream = FileInputStream(fd.fileDescriptor)
                val file = File(
                    appContext.cacheDir,
                    appContext.contentResolver.getFileName(contentUri)
                )
                val outputStream = FileOutputStream(file)
                inputStream.copyTo(outputStream)
                file
            }
        } ?: return Resource.Error(
            uiText = UiText.StringResource(R.string.unknown_error)
        )

        return try {
            val response = api.createPost(
                postData = MultipartBody.Part.createFormData(
                    "post_data",
                    gson.toJson(request)
                ),
                postContent = MultipartBody.Part.createFormData(
                    name = "post_content",
                    filename = file.name,
                    body = file.asRequestBody()
                )
            )

            if (response.successful) {
                Resource.Success(uiText = null)
            } else {
                response.message?.let {
                    Resource.Error(uiText = UiText.CallResponseText(it))
                } ?: Resource.Error(data = null, uiText = UiText.unknownError())
            }
        } catch (e: IOException) {
            Resource.Error(uiText = UiText.StringResource(R.string.fail_to_connect))
        } catch (e: HttpException) {
            Resource.Error(uiText = UiText.StringResource(R.string.fail_to_connect))
        }
    }
}
