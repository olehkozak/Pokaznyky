package pokaznyky.lviv

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.support.v4.app.NotificationCompat
import android.support.v4.app.TaskStackBuilder
import android.util.Log
import pokaznyky.lviv.ui.activity.MainActivity
import pokaznyky.lviv.util.AlarmUtil
import pokaznyky.lviv.util.constant.NotificationConstants


/**
 * Created by olehkozak on 12/19/17.
 */
class AlarmReceiver : BroadcastReceiver() {

    companion object {
        val TAG = AlarmReceiver::class.java.simpleName
    }

    override fun onReceive(context: Context, intent: Intent?) {
        Log.i(TAG, "onReceive")

        val mBuilder = NotificationCompat.Builder(context, NotificationConstants.ALARM_CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentText(NotificationConstants.ALARM_NOTIFICATION_TEXT)

        val resultIntent = Intent(context, MainActivity::class.java)

        val stackBuilder = TaskStackBuilder.create(context)
        stackBuilder.addParentStack(MainActivity::class.java)
        stackBuilder.addNextIntent(resultIntent)

        val resultPendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT)
        mBuilder.setContentIntent(resultPendingIntent)

        val mNotificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val mChannel: NotificationChannel
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val importance = NotificationManager.IMPORTANCE_HIGH
            mChannel = NotificationChannel(NotificationConstants.ALARM_CHANNEL_ID, NotificationConstants.ALARM_CHANNEL_NAME, importance)
            mChannel.description = NotificationConstants.ALARM_CHANNEL_DESCRIPTION
            mChannel.enableLights(true)
            mChannel.lightColor = Color.RED
            mChannel.enableVibration(true)
            mChannel.vibrationPattern = longArrayOf(100, 200, 300, 400, 500, 400, 300, 200, 400)
            mNotificationManager.createNotificationChannel(mChannel)
        }

        mNotificationManager.notify(NotificationConstants.ALARM_NOTIFICATION_ID, mBuilder.build())
        AlarmUtil.resetAlarm(context)
    }
}