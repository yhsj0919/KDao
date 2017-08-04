package xyz.yhsj.kdbexaple

import android.app.Application
import xyz.yhsj.kdb.Kdb
import xyz.yhsj.kdb.sqlite.KdbConfig

/**App
 * Created by LOVE on 2017/8/4 004.
 */
class BaseApp : Application() {
    override fun onCreate() {
        super.onCreate()
        Kdb.isDebug = true
        Kdb.init(this, KdbConfig("kdb_example", null, 1))
    }
}