package com.code.block.core.util

import android.content.Context
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.core.app.NotificationCompat
import com.onesignal.OSNotificationReceivedEvent
import com.onesignal.OneSignal.OSRemoteNotificationReceivedHandler

@SuppressWarnings("unused")
class NotificationServiceExtension : OSRemoteNotificationReceivedHandler {
    override fun remoteNotificationReceived(
        p0: Context?,
        p1: OSNotificationReceivedEvent?,
    ) {
        val notification = p1?.notification
        val mutableNotification = notification?.mutableCopy()
        mutableNotification?.setExtender {
            it.apply {
                color = Color.White.toArgb()
                val spannableTitle = SpannableString(notification.title).apply {
                    setSpan(
                        /* what = */ ForegroundColorSpan(Color.Blue.toArgb()),
                        /* start = */ 0,
                        /* end = */ notification.title.length,
                        /* flags = */ 0,
                    )
                }
                setContentTitle(spannableTitle)
                val spannableBody = SpannableString(notification.body).apply {
                    setSpan(
                        /* what = */ ForegroundColorSpan(Color.Black.toArgb()),
                        /* start = */ 0,
                        /* end = */ notification.body.length,
                        /* flags = */ 0,
                    )
                }
                setContentText(spannableBody)
                setTimeoutAfter(30000)
                setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
            }
        }
        val data = notification?.additionalData
        p1?.complete(mutableNotification)
    }
}
