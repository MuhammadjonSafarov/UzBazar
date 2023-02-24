/*
package uz.xia.bazar

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.graphics.Color
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import uz.xia.bazar.ui.auth.CHANNEL_ID
import uz.xia.bazar.ui.auth.NOTIFICATION_CHANNEL_ID

private const val TAG = "MyNotificationService"

class MyNotificationService : FirebaseMessagingService() {
    override fun onMessageReceived(result: RemoteMessage) {
        showNotification(result)
    }

    private fun showNotification(result: RemoteMessage) {
        val notificationManager =getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val notification = NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID)
            .setContentTitle(result.notification!!.title)
            .setContentText(result.notification!!.body)
            .setColor(resources.getColor(R.color.black))
            .setVibrate(longArrayOf(0, 500, 1000, 500))
            .setDefaults(Notification.DEFAULT_LIGHTS)
            .setSmallIcon(R.drawable.ic_stat_name)
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.O) {
            val notificationChannel = NotificationChannel(
                CHANNEL_ID, "notification_channel_id", NotificationManager.IMPORTANCE_HIGH)
            */
/* val audioAttributes = AudioAttributes.Builder()
                 .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                 .setUsage(AudioAttributes.USAGE_NOTIFICATION)
                 .build()*//*

            notificationChannel.enableLights(true)
            // notificationChannel.setSound(soundUri, audioAttributes)
            notificationChannel.lightColor = Color.GREEN
            notificationChannel.enableVibration(true)
            notification.setChannelId(CHANNEL_ID)
            notificationManager.createNotificationChannel(notificationChannel)
        }
        notificationManager.notify(4, notification.build())
    }

    override fun onNewToken(token: String) {
        Log.d(TAG,"token : $token")
        super.onNewToken(token)
    }
}*/
