package xyz.yhsj.kdao

import android.app.Application
import xyz.yhsj.kdao.sqlite.KdaoConfig
import xyz.yhsj.kdao.sqlite.KdaoDatabaseOpenHelper

/**
 * Created by LOVE on 2017/8/4 004.
 */
object Kdao {
    var isDebug = false
    lateinit var database: KdaoDatabaseOpenHelper

    fun init(baseApp: Application, config: KdaoConfig) {
        database = KdaoDatabaseOpenHelper.getInstance(baseApp, config)
    }
}