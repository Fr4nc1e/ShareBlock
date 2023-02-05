package com.code.block.di

import android.content.SharedPreferences
import com.code.block.core.util.Constants.BASE_URL
import com.code.block.feature.post.data.source.PostApi
import com.code.block.feature.profile.data.repository.ProfileRepositoryImpl
import com.code.block.feature.profile.data.source.ProfileApi
import com.code.block.feature.profile.domain.repository.ProfileRepository
import com.code.block.usecase.profile.* // ktlint-disable no-wildcard-imports
import com.code.block.usecase.profile.components.* // ktlint-disable no-wildcard-imports
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
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
            .create(ProfileApi::class.java)
    }

    @Provides
    @Singleton
    fun provideProfileRepository(
        profileApi: ProfileApi,
        postApi: PostApi,
        gson: Gson,
        sharedPreferences: SharedPreferences
    ): ProfileRepository {
        return ProfileRepositoryImpl(
            profileApi = profileApi,
            postApi = postApi,
            gson = gson,
            sharedPreferences = sharedPreferences
        )
    }

    @Provides
    @Singleton
    fun provideProfileUseCases(repository: ProfileRepository): ProfileUseCases {
        return ProfileUseCases(
            getProfileUseCase = GetProfileUseCase(repository),
            updateProfileUseCase = UpdateProfileUseCase(repository),
            getOwnPostsProfileUseCase = GetOwnPostsProfileUseCase(repository),
            getLikedPostsProfileUseCase = GetLikedPostsProfileUseCase(repository),
            searchUseCase = SearchUseCase(repository),
            followUserUseCase = FollowUserUseCase(repository),
            commentsUseCase = GetCommentsUseCase(repository),
            logoutUseCase = LogoutUseCase(repository),
            getFollowersUseCase = GetFollowersUseCase(repository),
            getFollowingsUseCase = GetFollowingsUseCase(repository)
        )
    }

    @Provides
    @Singleton
    fun provideFollowUserUseCase(repository: ProfileRepository): FollowUserUseCase {
        return FollowUserUseCase(repository)
    }
}
