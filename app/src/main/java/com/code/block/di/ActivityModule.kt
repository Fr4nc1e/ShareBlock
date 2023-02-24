package com.code.block.di

import com.code.block.core.util.Constants.BASE_URL
import com.code.block.feature.activity.data.repository.ActivityRepositoryImpl
import com.code.block.feature.activity.data.source.api.ActivityApi
import com.code.block.feature.activity.domain.repository.ActivityRepository
import com.code.block.usecase.activity.GetActivitiesUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
@InstallIn(SingletonComponent::class)
object ActivityModule {
    @Provides
    @Singleton
    fun provideActivityApi(client: OkHttpClient): ActivityApi {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ActivityApi::class.java)
    }

    @Provides
    @Singleton
    fun provideActivityRepository(api: ActivityApi): ActivityRepository {
        return ActivityRepositoryImpl(api)
    }

    @Provides
    @Singleton
    fun provideGetActivitiesUseCase(repository: ActivityRepository): GetActivitiesUseCase {
        return GetActivitiesUseCase(repository)
    }
}
