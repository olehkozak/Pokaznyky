package pokaznyky.lviv.util

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import org.joda.time.DateTime
import org.joda.time.DateTimeConstants
import org.joda.time.LocalDate
import pokaznyky.lviv.AlarmReceiver

/**
 * Created by olehkozak on 12/21/17.
 */
class AlarmUtil {
    companion object {
        private var intent: Intent? = null
        private var pendingIntent: PendingIntent? = null
        private var alarmManager: AlarmManager? = null

        private val ALARM_HOUR = 20
        private val ALARM_MINUTE = 0
        private val ALARM_SECOND = 0

        fun setAlarm(context: Context) {
            setAlarm(context, false)
        }

        fun setAlarm(context: Context, forceNextMonth: Boolean) {
            configAlarmData(context)
            alarmManager?.set(AlarmManager.RTC_WAKEUP, getTriggerAlarmTime(forceNextMonth), pendingIntent)
        }

        fun resetAlarm(context: Context) {
            cancelAlarm(context)
            setAlarm(context, true)
        }

        fun cancelAlarm(context: Context) {
            configAlarmData(context)
            alarmManager?.cancel(pendingIntent)
        }

        private fun configAlarmData(context: Context) {
            resetAlarmData()
            intent = Intent(context, AlarmReceiver::class.java)
            pendingIntent = PendingIntent.getBroadcast(context, 1, intent, 0)
            alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        }

        private fun resetAlarmData() {
            intent = null
            pendingIntent = null
            alarmManager = null
        }

        private fun DateTime.timeMillis(): Long {
            return this.toDate().time
        }

        private fun getTriggerAlarmTime(forceNextMonth: Boolean): Long {
            val now = DateTime.now()
            val lastMondayThisMonth = getLastMondayOfMonth(now)
            if (!forceNextMonth && lastMondayThisMonth.isAfterNow) {
                return lastMondayThisMonth.timeMillis()
            }
            return getLastMondayOfMonth(now.plusMonths(1)).timeMillis()
        }

        private fun getLastMondayOfMonth(dateTime: DateTime): DateTime {
            val localDate = getNthOfMonth(DateTimeConstants.TUESDAY, dateTime.monthOfYear, dateTime.year)
            return DateTime(localDate.year, localDate.monthOfYear, localDate.dayOfMonth, ALARM_HOUR, ALARM_MINUTE, ALARM_SECOND)
        }

        private fun getNthOfMonth(dayOfWeek: Int, month: Int, year: Int): LocalDate {
            val date = LocalDate(year, month, 1).dayOfMonth()
                    .withMaximumValue()
                    .dayOfWeek()
                    .setCopy(dayOfWeek)
            return if (date.monthOfYear != month) {
                date.dayOfWeek().addToCopy(-7)
            } else date
        }

    }
}