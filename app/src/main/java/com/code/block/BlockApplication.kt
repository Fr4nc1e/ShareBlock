package com.code.block

import android.app.Application
import com.code.block.core.util.Constants
import com.onesignal.OneSignal
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class BlockApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        OneSignal.setLogLevel(OneSignal.LOG_LEVEL.VERBOSE, OneSignal.LOG_LEVEL.NONE)
        OneSignal.initWithContext(this)
        OneSignal.promptForPushNotifications()
        OneSignal.setAppId(Constants.ONESIGNAL_APP_ID)
    }
}
