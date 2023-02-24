package uz.xia.bazar.service

import android.app.IntentService
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.IBinder
import android.util.Log
import androidx.core.app.NotificationCompat
import uz.xia.bazar.R
import uz.xia.bazar.ui.auth.CHANNEL_ID
import uz.xia.bazar.ui.auth.NOTIFICATION_CHANNEL_ID

class MyService : IntentService("intent_service") {
    override fun onBind(p0: Intent?): IBinder? {
        return null
    }

    override fun onHandleIntent(p0: Intent?) {
        for (it in 0..100) {
            Thread.sleep(1000)
            Log.d("TAG_LOG", "onCreate  $it")
        }
        showNotification()
    }

    private fun showNotification() {
        val notificationManager =getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val notification = NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID)
            .setContentTitle("result.notification!!.title")
            .setContentText("result.notification!!.body")
            .setColor(resources.getColor(R.color.black))
            .setVibrate(longArrayOf(0, 500, 1000, 500))
            .setDefaults(Notification.DEFAULT_LIGHTS)
            .setSmallIcon(R.drawable.ic_stat_name)
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.O) {
            val notificationChannel = NotificationChannel(
                CHANNEL_ID, "notification_channel_id", NotificationManager.IMPORTANCE_HIGH)
            /* val audioAttributes = AudioAttributes.Builder()
                 .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                 .setUsage(AudioAttributes.USAGE_NOTIFICATION)
                 .build()*/
            notificationChannel.enableLights(true)
            // notificationChannel.setSound(soundUri, audioAttributes)
            notificationChannel.lightColor = Color.GREEN
            notificationChannel.enableVibration(true)
            notification.setChannelId(CHANNEL_ID)
            notificationManager.createNotificationChannel(notificationChannel)
        }
        notificationManager.notify(4, notification.build())
    }

/*    override fun onCreate() {
        super.onCreate()

    }*/

}