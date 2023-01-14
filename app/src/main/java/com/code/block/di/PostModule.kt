package com.code.block.di

import com.code.block.feature.post.data.repository.PostRepositoryImpl
import com.code.block.feature.post.data.source.remote.PostApi
import com.code.block.feature.post.domain.repository.PostRepository
import com.code.block.feature.post.domain.usecase.GetPostsForFollowUseCase
import com.code.block.feature.post.domain.usecase.PostUseCases
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
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
            .baseUrl(PostApi.BASE_URL)
            .client(client)
            .build()
            .create(PostApi::class.java)
    }

    @Provides
    @Singleton
    fun providePostRepository(api: PostApi): PostRepository {
        return PostRepositoryImpl(api = api)
    }

    @Provides
    @Singleton
    fun providePostUseCases(repository: PostRepository): PostUseCases {
        return PostUseCases(
            getPostsForFollowUseCase = GetPostsForFollowUseCase(
                repository = repository
            )
        )
    }
}
