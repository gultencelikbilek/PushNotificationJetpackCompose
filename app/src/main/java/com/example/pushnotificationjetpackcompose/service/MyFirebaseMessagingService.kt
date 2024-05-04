package com.example.pushnotificationjetpackcompose.service

import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build
import android.os.Build.*
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import com.example.pushnotificationjetpackcompose.R
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class MyFirebaseMessagingService :
    FirebaseMessagingService() { //Bildirim almak için ve token güncellemek için kullanılan bir sınıftır.

    companion object {
        private const val TAG = "FCM Notification"
        const val DEFAULT_NOTIFICATION_ID = 0
    }

    override fun onNewToken(token: String) { // yeni bir token oluşturulduğunda bu fonksiyon çağırılır

        Log.i(TAG, "new FCM token created: $token")
        var notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        initNotificaitonChannel(notificationManager)
    }

    override fun onMessageReceived(message: RemoteMessage) { //Bildiirm alındığında çağırılan bir methoddur.

        //Bildirimin başlığı ve içeriği alınır ardından bildiirm oluşturulur ve kullanıcıya gösterilir.
        val title = message.notification?.title
        val body = message.notification?.body

        val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager

        var notificationBuilder = if (VERSION_CODES.O <= VERSION.SDK_INT) {
            NotificationCompat.Builder(applicationContext, "1")
        } else {

            NotificationCompat.Builder(applicationContext)
        }

        notificationBuilder = notificationBuilder
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle(title)
            .setContentText(body)
            .setAutoCancel(true)
        initNotificaitonChannel(notificationManager)
        notificationManager.notify(DEFAULT_NOTIFICATION_ID, notificationBuilder.build())
    }

    private fun initNotificaitonChannel(notificationManager: NotificationManager) {
        if (VERSION_CODES.O <= VERSION.SDK_INT) {
            notificationManager.createNotificationChannelIfNotExists(
                channelId = "1",
                channelName = "com.example.pushnotificationjetpackcompose.service"
            )
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
fun NotificationManager.createNotificationChannelIfNotExists(
    channelId: String,
    channelName: String,
    importance: Int = NotificationManager.IMPORTANCE_DEFAULT
) {
    var channel = this.getNotificationChannel(channelId)

    if (channel == null) {
        channel = NotificationChannel(
            channelId,
            channelName,
            importance
        )
        this.createNotificationChannel(channel)
    }
}