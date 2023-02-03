package com.code.block.di

import android.app.Application
import com.code.block.feature.chat.data.source.ws.util.FlowStreamAdapter
import com.code.block.feature.chat.data.source.ws.util.GsonMessageAdapter
import com.google.gson.Gson
import com.tinder.scarlet.Scarlet
import com.tinder.scarlet.lifecycle.android.AndroidLifecycle
import com.tinder.scarlet.websocket.okhttp.newWebSocketFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ChatModule {
    @Provides
    @Singleton
    fun provideScarlet(app: Application, gson: Gson, client: OkHttpClient): Scarlet {
        return Scarlet.Builder()
            .addMessageAdapterFactory(GsonMessageAdapter.Factory(gson))
            .addStreamAdapterFactory(FlowStreamAdapter.Factory)
            .webSocketFactory(
                client.newWebSocketFactory("ws://172.28.211.51:8081/api/chat/websocket")
            )
            .lifecycle(AndroidLifecycle.ofApplicationForeground(app))
            .build()
    }
}
