package xyz.yhsj.kdb.sqlite.parser

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import org.jetbrains.anko.db.MapRowParser

/**数据反解析
 * Created by LOVE on 2017/8/4 004.
 */


inline fun <reified T : Any> classParser(): MapRowParser<T> {

    return object : MapRowParser<T> {
        override fun parseRow(columns: Map<String, Any?>): T {
            return Gson().fromJson(Gson().toJson(columns), object : TypeToken<T>() {}.type)
        }

    }
}