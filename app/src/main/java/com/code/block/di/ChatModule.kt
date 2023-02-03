package com.code.block.di

import com.code.block.core.util.Constants.BASE_URL
import com.code.block.feature.chat.data.repository.ChatRepositoryImpl
import com.code.block.feature.chat.data.source.ChatApi
import com.code.block.feature.chat.domain.repository.ChatRepository
import com.code.block.feature.chat.domain.usecase.ChatUseCases
import com.code.block.feature.chat.domain.usecase.component.* // ktlint-disable no-wildcard-imports
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
object ChatModule {
    @Provides
    fun provideChatUseCases(repository: ChatRepository): ChatUseCases {
        return ChatUseCases(
            getMessagesForChatUseCase = GetMessagesForChatUseCase(repository),
            getChatsForUserUseCase = GetChatsForUserUseCase(repository),
            observeChatEvents = ObserveChatEvents(repository),
            observeMessages = ObserveMessages(repository),
            sendMessage = SendMessage(repository),
            initRepositoryUseCase = InitRepositoryUseCase(repository)
        )
    }

    @Provides
    @Singleton
    fun provideChatApi(client: OkHttpClient): ChatApi {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
            .create(ChatApi::class.java)
    }

    @Provides
    fun provideChatRepository(client: OkHttpClient, chatApi: ChatApi): ChatRepository {
        return ChatRepositoryImpl(
            chatApi = chatApi,
            okHttpClient = client
        )
    }
}
