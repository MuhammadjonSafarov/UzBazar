package uz.xia.bazar

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.fragment.app.Fragment
import uz.xia.bazar.service.MyService
import uz.xia.bazar.ui.main.*

class MainActivity : AppCompatActivity(), ILoginListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            addFragment(LoginFragment.newInstance())
        }
    }

    private fun addFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().replace(R.id.container, fragment).commitNow()

        val intent = Intent(this, MyService::class.java)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            startForegroundService(intent)
        } else {
            startService(intent)
        }
        //  createNotification()
    }

    override fun onClickLogin() {
        addFragment(MainFragment.newInstance())
    }

    private fun createNotification() {
        val notificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
/*        val notification = NotificationCompat.Builder(requireContext())
            .setContentTitle("My Notification")
            .setContentText("text nimadur yangilik haqida")
            .setColor(Color.parseColor("#00ae3f"))
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setSmallIcon(R.drawable.ic_stat_name)
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.O) {
            val notificationChannel = NotificationChannel(
                "notification_id",
                "notification_channel_name",
                NotificationManager.IMPORTANCE_HIGH
            )
            notificationChannel.lightColor= Color.WHITE
            notificationChannel.enableVibration(true)
            notificationManager.createNotificationChannel(notificationChannel)
        }
        notification.setChannelId(NOTIFICATION_CHANNEL_ID)
        notificationManager.notify(2,notification.build())*/


        val notification = NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID)
            .setContentTitle("result.notification!!.title")
            .setContentText("result.notification!!.body")
            .setColor(resources.getColor(R.color.black)).setVibrate(longArrayOf(0, 500, 1000, 500))
            .setDefaults(Notification.DEFAULT_LIGHTS).setSmallIcon(R.drawable.ic_stat_name)
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.O) {
            val notificationChannel = NotificationChannel(
                CHANNEL_ID, "notification_channel_id", NotificationManager.IMPORTANCE_HIGH
            )
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

}