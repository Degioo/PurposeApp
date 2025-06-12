import android.Manifest
import android.R
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.annotation.RequiresPermission
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import kotlin.random.Random

class ReminderReceiver : BroadcastReceiver() {
    @RequiresPermission(Manifest.permission.POST_NOTIFICATIONS)
    override fun onReceive(context: Context, intent: Intent) {
        val title = intent.getStringExtra("title") ?: "Evento"

        val builder = NotificationCompat.Builder(context, "reminder_channel")
            .setSmallIcon(R.drawable.ic_dialog_info) // icona base, puoi cambiarla
            .setContentTitle("Promemoria")
            .setContentText("Sta per iniziare: $title")
            .setPriority(NotificationCompat.PRIORITY_HIGH)

        // Mostra la notifica
        NotificationManagerCompat.from(context).notify(Random.nextInt(), builder.build())
    }
}
