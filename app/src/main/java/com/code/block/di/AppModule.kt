package com.code.block.di

import android.app.Application
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import com.code.block.core.data.source.OneSignalService
import com.code.block.core.usecase.GetOwnUserIdUseCase
import com.code.block.core.usecase.notification.NotificationUseCases
import com.code.block.core.usecase.notification.SendPostNotificationUseCase
import com.code.block.core.util.Constants
import com.code.block.core.util.ui.liker.Liker
import com.code.block.core.util.ui.liker.LikerImpl
import com.code.block.feature.post.domain.repository.PostRepository
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideSharedPref(app: Application): SharedPreferences {
        return app.getSharedPreferences(
            Constants.SHARED_PREF_NAME,
            MODE_PRIVATE
        )
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(sharedPreferences: SharedPreferences): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor {
                val token = sharedPreferences.getString(Constants.KEY_JWT_TOKEN, "")
                val modifiedRequest = it.request().newBuilder()
                    .addHeader(
                        "Authorization",
                        "Bearer $token"
                    )
                    .build()
                it.proceed(modifiedRequest)
            }
            .addInterceptor(
                HttpLoggingInterceptor().apply {
                    level = HttpLoggingInterceptor.Level.BODY
                }
            )
            .build()
    }

    @Provides
    @Singleton
    fun provideOneSignalService(client: OkHttpClient): OneSignalService {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(Constants.BASE_URL)
            .client(client)
            .build()
            .create(OneSignalService::class.java)
    }

    @Provides
    @Singleton
    fun provideGson(): Gson {
        return Gson()
    }

    @Provides
    @Singleton
    fun provideGetOwnUserIdUseCase(sharedPreferences: SharedPreferences): GetOwnUserIdUseCase {
        return GetOwnUserIdUseCase(sharedPreferences)
    }

    @Provides
    @Singleton
    fun provideNotificationUseCase(repository: PostRepository): NotificationUseCases {
        return NotificationUseCases(
            sendPostNotificationUseCase = SendPostNotificationUseCase(repository)
        )
    }

    @Provides
    @Singleton
    fun provideLiker(): Liker {
        return LikerImpl()
    }
}
