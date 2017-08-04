package xyz.yhsj.kdb.sqlite

import android.database.sqlite.SQLiteDatabase

/**数据库配置
 * Created by LOVE on 2017/8/4 004.
 */
data class KdbConfig(
        val name: String = "Kdb",
        val factory: SQLiteDatabase.CursorFactory? = null,
        val version: Int = 1
)