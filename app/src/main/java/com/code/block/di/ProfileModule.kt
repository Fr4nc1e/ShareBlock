package com.code.block.di

import com.code.block.feature.profile.data.repository.ProfileRepositoryImpl
import com.code.block.feature.profile.data.source.ProfileApi
import com.code.block.feature.profile.domain.repository.ProfileRepository
import com.code.block.feature.profile.domain.usecase.GetProfileUseCase
import com.code.block.feature.profile.domain.usecase.ProfileUseCases
import com.code.block.feature.profile.domain.usecase.UpdateProfileUseCase
import com.google.gson.Gson
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
object ProfileModule {
    @Provides
    @Singleton
    fun provideProfileApi(client: OkHttpClient): ProfileApi {
        return Retrofit.Builder()
            .baseUrl(ProfileApi.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
            .create(ProfileApi::class.java)
    }

    @Provides
    @Singleton
    fun provideProfileRepository(api: ProfileApi, gson: Gson): ProfileRepository {
        return ProfileRepositoryImpl(api, gson)
    }

    @Provides
    @Singleton
    fun provideProfileUseCases(repository: ProfileRepository): ProfileUseCases {
        return ProfileUseCases(
            getProfileUseCase = GetProfileUseCase(repository),
            updateProfileUseCase = UpdateProfileUseCase(repository)
        )
    }
}
