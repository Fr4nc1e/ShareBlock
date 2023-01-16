package com.code.block.feature.post.data.repository

import android.annotation.SuppressLint
import android.net.Uri
import androidx.core.net.toFile
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.code.block.R
import com.code.block.core.domain.model.Post
import com.code.block.core.utils.Constants
import com.code.block.core.utils.CreatePostResource
import com.code.block.core.utils.Resource
import com.code.block.core.utils.UiText
import com.code.block.feature.post.data.source.paging.PostSource
import com.code.block.feature.post.data.source.remote.CreatePostRequest
import com.code.block.feature.post.data.source.remote.PostApi
import com.code.block.feature.post.domain.repository.PostRepository
import com.google.gson.Gson
import kotlinx.coroutines.flow.Flow
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import retrofit2.HttpException
import java.io.IOException

class PostRepositoryImpl(
    private val api: PostApi,
    private val gson: Gson
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

        val file = contentUri.toFile()

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
                response.message?.let {
                    Resource.Success(message = it, uiText = null)
                } ?: Resource.Success(uiText = null)
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
