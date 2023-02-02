package com.code.block.di

import android.content.Context
import com.code.block.core.util.Constants.BASE_URL
import com.code.block.feature.post.data.repository.PostRepositoryImpl
import com.code.block.feature.post.data.source.PostApi
import com.code.block.feature.post.domain.repository.PostRepository
import com.code.block.usecase.post.* // ktlint-disable no-wildcard-imports
import com.code.block.usecase.post.components.* // ktlint-disable no-wildcard-imports
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object PostModule {

    @Provides
    @Singleton
    fun providePostApi(client: OkHttpClient): PostApi {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .client(client)
            .build()
            .create(PostApi::class.java)
    }

    @Provides
    @Singleton
    fun providePostRepository(
        api: PostApi,
        gson: Gson,
        @ApplicationContext appContext: Context
    ): PostRepository {
        return PostRepositoryImpl(
            api = api,
            gson = gson,
            appContext = appContext
        )
    }

    @Provides
    @Singleton
    fun providePostUseCases(repository: PostRepository): PostUseCases {
        return PostUseCases(
            getPostsForFollowUseCase = GetPostsForFollowUseCase(
                repository = repository
            ),
            createPostUseCase = CreatePostUseCase(
                repository = repository
            ),
            getCommentsForPostUseCase = GetCommentsForPostUseCase(
                repository = repository
            ),
            getPostDetailUseCase = GetPostDetailUseCase(
                repository = repository
            ),
            createCommentUseCase = CreateCommentUseCase(
                repository = repository
            ),
            likeParentUseCase = LikeParentUseCase(
                repository = repository
            ),
            getLikedUsersForParent = GetLikedUsersForParent(
                repository = repository
            )
        )
    }
}
