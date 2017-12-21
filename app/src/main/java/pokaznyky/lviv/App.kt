package pokaznyky.lviv

import android.app.Application
import net.danlew.android.joda.JodaTimeAndroid
import pokaznyky.lviv.util.AlarmUtil


/**
 * Created by olehkozak on 12/19/17.
 */
class App : Application() {
    override fun onCreate() {
        super.onCreate()
        JodaTimeAndroid.init(baseContext)
        AlarmUtil.setAlarm(baseContext)
    }
}