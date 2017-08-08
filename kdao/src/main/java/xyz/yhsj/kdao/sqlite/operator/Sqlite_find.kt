package xyz.yhsj.kdao.sqlite.operator

import xyz.yhsj.kdao.sqlite.annotation.PrimaryKey
import xyz.yhsj.kdao.sqlite.condition
import xyz.yhsj.kdao.sqlite.parser.classParser
import org.jetbrains.anko.db.SelectQueryBuilder
import org.jetbrains.anko.db.select
import xyz.yhsj.kdao.Kdao
import java.io.Serializable
import java.lang.Exception
import kotlin.reflect.full.declaredMemberProperties

/**查询
 * Created by LOVE on 2017/8/4 004.
 */


/**
 * 根据 主键 查询数据
 * */
inline fun <reified T : Serializable> T.findOneByKey(primaryKey: Any): T? {
    val mClass = this.javaClass.kotlin
    val name = "${mClass.simpleName}"
    val properties = mClass.declaredMemberProperties
    var propertyName: String? = null

    properties.forEach {
        if (it.annotations.map { it.annotationClass }.contains(PrimaryKey::class)) propertyName = it.name
    }
    if (propertyName == null) throw Exception("$name 类型没有设置PrimaryKey")
    return Kdao.database.use {
        tryDo {
            select(name).apply {
                limit(1)
                condition { propertyName!! equalsData  primaryKey.toString() }
            }.parseOpt(classParser<T>())
        }.let {
            when (it) {
                null, "no such table" -> null
                is T -> it
                else -> null
            }
        }
    }
}


inline fun <reified T : Serializable> T.findOne(crossinline functor: SelectQueryBuilder.() -> Unit): T? {
    val name = "${this.javaClass.kotlin.simpleName}"

    return Kdao.database.use {
        tryDo {
            select(name).apply {
                limit(1)
                functor()
            }.parseOpt(classParser<T>())
        }.let {
            when (it) {
                null, "no such table" -> null
                is T -> it
                else -> null
            }
        }

    }
}

inline fun <reified T : Serializable> T.findAll(crossinline functor: SelectQueryBuilder.() -> Unit): List<T> {
    val name = "${this.javaClass.kotlin.simpleName}"

    return Kdao.database.use {
        tryDo {
            select(name).apply {
                functor()
            }.parseList(classParser<T>())
        }.let {
            when (it) {
                null, "no such table" -> emptyList()
                is List<*> -> it as List<T>
                else -> emptyList()
            }
        }
    }
}
