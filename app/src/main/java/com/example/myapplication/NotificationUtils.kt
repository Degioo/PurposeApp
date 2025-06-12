package com.example.myapplication

import android.app.*
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import java.util.Calendar

object NotificationUtils {
    private const val CHANNEL_ID = "purpose_channel"

    fun createChannel(ctx: Context) {
        val nm = ctx.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val ch = NotificationChannel(CHANNEL_ID, "Propositi", NotificationManager.IMPORTANCE_HIGH)
            nm.createNotificationChannel(ch)
        }
    }

    fun scheduleNotification(ctx: Context, title: String, whenMs: Long) {
        val intent = Intent(ctx, AlarmReceiver::class.java).apply {
            putExtra("title", title)
        }
        val pi = PendingIntent.getBroadcast(ctx, title.hashCode(), intent, PendingIntent.FLAG_UPDATE_CURRENT)
        val am = ctx.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        am.setExact(AlarmManager.RTC_WAKEUP, whenMs, pi)
    }

    fun Context.scheduleDailyNotification(title: String) {
        val alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(this, AlarmReceiver::class.java).apply {
            putExtra("title", title)
        }
        val pendingIntent = PendingIntent.getBroadcast(
            this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT
        )
        val cal = Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, 8)
            set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0)
            if (before(Calendar.getInstance())) add(Calendar.DATE, 1)
        }
        alarmManager.setInexactRepeating(
            AlarmManager.RTC_WAKEUP, cal.timeInMillis,
            AlarmManager.INTERVAL_DAY, pendingIntent
        )
    }

}

class AlarmReceiver : BroadcastReceiver() {
    override fun onReceive(c: Context, i: Intent) {
        val title = i.getStringExtra("title") ?: "È ora del tuo proposito!"
        val n = NotificationCompat.Builder(c, "purpose_channel")
            .setContentTitle("Promemoria")
            .setContentText(title)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .build()
        (c.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager).notify(title.hashCode(), n)
    }
}
