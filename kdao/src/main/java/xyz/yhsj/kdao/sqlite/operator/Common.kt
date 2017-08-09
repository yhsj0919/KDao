package xyz.yhsj.kdao.sqlite.operator

import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException
import com.google.gson.Gson
import xyz.yhsj.kdao.sqlite.annotation.Ignore
import xyz.yhsj.kdao.sqlite.annotation.PrimaryKey
import xyz.yhsj.kdao.sqlite.e
import java.io.Serializable
import java.lang.Exception
import kotlin.reflect.full.declaredMemberProperties
import kotlin.reflect.jvm.isAccessible

/**异常捕获处理
 * Created by LOVE on 2017/8/4 004.
 */
inline fun SQLiteDatabase.tryDo(functor: SQLiteDatabase.() -> Any?): Any? {
    return try {
        functor()
    } catch (ex: SQLiteException) {
        if (ex.message?.contains("no such table") == true) "no such table"
        else throw ex
    }
}

/**
 * 反射数据解析
 * Created by LOVE on 2017/8/4 004.
 */
fun <T : Serializable> getValuePairs(data: T): Map<String, Any> {
    val mClass = data.javaClass.kotlin
    val properties = mClass.declaredMemberProperties
    var havePrimaryKey: Boolean = false

    properties
            .forEach {
                if (it.annotations.map { it.annotationClass }.contains(PrimaryKey::class)) havePrimaryKey = true
            }

    if (!havePrimaryKey) throw Exception("${mClass.simpleName} 类型没有设置PrimaryKey")

    return properties
            .filter {
                it.isAccessible = true
                it.get(data) != null
            }
            .filter {
                !it.annotations.map { it.annotationClass }.contains(Ignore::class)
            }
            .associate {
                //设置可访问属性
                it.isAccessible = true
                "".e("save", "${it.name}  ->  ${it.get(data)}")
                it.name to it.get(data).let {
                    when (it) {
                        true -> "true"
                        false -> "false"
                        is String, is Long, is Int, is Float, is Double -> it
                        else -> Gson().toJson(it)
                    }
                }
            }
}



