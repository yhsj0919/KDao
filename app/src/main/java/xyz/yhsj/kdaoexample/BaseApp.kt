package xyz.yhsj.kdaoexample

import android.app.Application
import xyz.yhsj.kdao.Kdao
import xyz.yhsj.kdao.sqlite.KdaoConfig

/**App
 * Created by LOVE on 2017/8/4 004.
 */
class BaseApp : Application() {
    override fun onCreate() {
        super.onCreate()
        Kdao.isDebug = true
        Kdao.init(this, KdaoConfig("kdao_example", null, 1))
    }
}