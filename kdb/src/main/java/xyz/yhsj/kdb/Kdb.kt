package xyz.yhsj.kdb

import android.app.Application
import xyz.yhsj.kdb.sqlite.KdbConfig
import xyz.yhsj.kdb.sqlite.KdbDatabaseOpenHelper

/**
 * Created by LOVE on 2017/8/4 004.
 */
object Kdb {
    var isDebug = false
    lateinit var database: KdbDatabaseOpenHelper

    fun init(baseApp: Application, config: KdbConfig) {
        database = KdbDatabaseOpenHelper.getInstance(baseApp, config)
    }
}