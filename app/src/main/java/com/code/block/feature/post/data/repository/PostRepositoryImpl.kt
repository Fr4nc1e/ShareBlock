package com.code.block.feature.post.data.repository

import android.annotation.SuppressLint
import android.content.Context
import android.net.Uri
import com.code.block.R
import com.code.block.core.domain.resource.* // ktlint-disable no-wildcard-imports
import com.code.block.core.domain.util.* // ktlint-disable no-wildcard-imports
import com.code.block.core.util.FileNameReader.getFileName
import com.code.block.core.util.ui.UiText
import com.code.block.feature.post.data.source.PostApi
import com.code.block.feature.post.data.source.request.CreateCommentRequest
import com.code.block.feature.post.data.source.request.CreatePostRequest
import com.code.block.feature.post.data.source.request.LikeUpdateRequest
import com.code.block.feature.post.domain.repository.PostRepository
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
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
    private val appContext: Context,
) : PostRepository {

    override suspend fun getHomePosts(
        page: Int,
        pageSize: Int,
    ): HomePostsResource {
        return try {
            val posts = api.getHomePosts(
                page = page,
                pageSize = pageSize,
            ).map { it.toPost() }
            Resource.Success(data = posts, uiText = null)
        } catch (e: IOException) {
            Resource.Error(
                uiText = UiText.StringResource(R.string.fail_to_connect),
            )
        } catch (e: HttpException) {
            Resource.Error(
                uiText = UiText.StringResource(R.string.fail_to_connect),
            )
        }
    }

    @SuppressLint("Recycle")
    override suspend fun createPost(
        description: String,
        contentUri: Uri,
    ): CreatePostResource {
        val request = CreatePostRequest(description)

        val file = withContext(Dispatchers.IO) {
            appContext.contentResolver.openFileDescriptor(contentUri, "r")?.let { fd ->
                val inputStream = FileInputStream(fd.fileDescriptor)
                val file = File(
                    appContext.cacheDir,
                    appContext.contentResolver.getFileName(contentUri),
                )
                val outputStream = FileOutputStream(file)
                inputStream.copyTo(outputStream)
                file
            }
        } ?: return Resource.Error(
            uiText = UiText.StringResource(R.string.unknown_error),
        )

        return try {
            val response = api.createPost(
                postData = MultipartBody.Part.createFormData(
                    "post_data",
                    gson.toJson(request),
                ),
                postContent = MultipartBody.Part.createFormData(
                    name = "post_content",
                    filename = file.name,
                    body = file.asRequestBody(),
                ),
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

    override suspend fun getPostDetail(postId: String): PostDetailResource {
        return try {
            val response = api.getPostDetails(postId = postId)
            if (response.successful) {
                Resource.Success(
                    data = response.data?.toPost(),
                    uiText = null,
                )
            } else {
                response.message?.let { msg ->
                    Resource.Error(uiText = UiText.CallResponseText(msg))
                } ?: Resource.Error(uiText = UiText.StringResource(R.string.unknown_error))
            }
        } catch (e: IOException) {
            Resource.Error(
                uiText = UiText.StringResource(R.string.fail_to_connect),
            )
        } catch (e: HttpException) {
            Resource.Error(
                uiText = UiText.StringResource(R.string.fail_to_connect),
            )
        }
    }

    override suspend fun getCommentsForPost(postId: String): CommentsForPostResource {
        return try {
            val response = api.getCommentsForPost(postId = postId)
            if (response.successful) {
                Resource.Success(
                    data = response.data?.map { it.toComment() },
                    uiText = null,
                )
            } else {
                response.message?.let { msg ->
                    Resource.Error(uiText = UiText.CallResponseText(msg))
                } ?: Resource.Error(uiText = UiText.StringResource(R.string.unknown_error))
            }
        } catch (e: IOException) {
            Resource.Error(
                uiText = UiText.StringResource(R.string.fail_to_connect),
            )
        } catch (e: HttpException) {
            Resource.Error(
                uiText = UiText.StringResource(R.string.fail_to_connect),
            )
        }
    }

    override suspend fun createComment(
        comment: String,
        postId: String,
    ): CreateCommentResource {
        return try {
            val request = CreateCommentRequest(
                comment = comment,
                postId = postId,
            )
            val response = api.createComment(request)
            if (response.successful) {
                Resource.Success(uiText = null)
            } else {
                response.message?.let { msg ->
                    Resource.Error(uiText = UiText.CallResponseText(msg))
                } ?: Resource.Error(uiText = UiText.StringResource(R.string.unknown_error))
            }
        } catch (e: IOException) {
            Resource.Error(
                uiText = UiText.StringResource(R.string.fail_to_connect),
            )
        } catch (e: HttpException) {
            Resource.Error(
                uiText = UiText.StringResource(R.string.fail_to_connect),
            )
        }
    }

    override suspend fun likeParent(
        parentId: String,
        parentType: Int,
    ): LikeUpdateResource {
        return try {
            val request = LikeUpdateRequest(
                parentId = parentId,
                parentType = parentType,
            )
            val response = api.likeParent(request)
            if (response.successful) {
                Resource.Success(uiText = null)
            } else {
                response.message?.let { msg ->
                    Resource.Error(uiText = UiText.CallResponseText(msg))
                } ?: Resource.Error(uiText = UiText.StringResource(R.string.unknown_error))
            }
        } catch (e: IOException) {
            Resource.Error(
                uiText = UiText.StringResource(R.string.fail_to_connect),
            )
        } catch (e: HttpException) {
            Resource.Error(
                uiText = UiText.StringResource(R.string.fail_to_connect),
            )
        }
    }

    override suspend fun unlikeParent(
        parentId: String,
        parentType: Int,
    ): LikeUpdateResource {
        return try {
            val response = api.unlikeParent(
                parentId = parentId,
                parentType = parentType,
            )
            if (response.successful) {
                Resource.Success(uiText = null)
            } else {
                response.message?.let { msg ->
                    Resource.Error(uiText = UiText.CallResponseText(msg))
                } ?: Resource.Error(uiText = UiText.StringResource(R.string.unknown_error))
            }
        } catch (e: IOException) {
            Resource.Error(
                uiText = UiText.StringResource(R.string.fail_to_connect),
            )
        } catch (e: HttpException) {
            Resource.Error(
                uiText = UiText.StringResource(R.string.fail_to_connect),
            )
        }
    }

    override suspend fun getLikedUsers(parentId: String): LikedUsersResource {
        return try {
            Resource.Success(
                data = api.getLikedUsers(parentId).data?.map { it.toUserItem() },
                uiText = null,
            )
        } catch (e: IOException) {
            Resource.Error(
                uiText = UiText.StringResource(R.string.fail_to_connect),
            )
        } catch (e: HttpException) {
            Resource.Error(
                uiText = UiText.StringResource(R.string.fail_to_connect),
            )
        }
    }

    override suspend fun deletePost(postId: String): DeleteResource {
        return try {
            api.deletePost(postId)
            Resource.Success(uiText = null)
        } catch (e: IOException) {
            Resource.Error(
                uiText = UiText.StringResource(R.string.fail_to_connect),
            )
        } catch (e: HttpException) {
            Resource.Error(
                uiText = UiText.StringResource(R.string.fail_to_connect),
            )
        }
    }
}
