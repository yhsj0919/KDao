package xyz.yhsj.kdb.sqlite.parser

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import org.jetbrains.anko.db.*
import xyz.yhsj.kdb.sqlite.annotation.PrimaryKey
import xyz.yhsj.kdb.sqlite.e
import java.lang.reflect.Modifier
import kotlin.reflect.full.declaredMemberProperties
import kotlin.reflect.full.starProjectedType
import kotlin.reflect.jvm.javaType

/**数据反解析
 * Created by LOVE on 2017/8/4 004.
 */


inline fun <reified T : Any> classParser(): MapRowParser<T> {

    val clazz = T::class.java
    val name = "${clazz.simpleName}"
    val properties = clazz.kotlin.declaredMemberProperties

    val constructors = clazz.declaredConstructors.filter {
        val types = it.parameterTypes
        !it.isVarArgs && Modifier.isPublic(it.modifiers) &&
                types != null && types.isNotEmpty()
    }
    val c = constructors[0]

    val tablePairs = properties
            .associate {
                it.name to it.returnType
            }.toList().toTypedArray()

    return object : MapRowParser<T> {
        override fun parseRow(columns: Map<String, Any?>): T {

            tablePairs.forEach { println("${it.first}   ->   ${it.second}") }

            val newColumns = tablePairs
                    .associate { (key, value) ->
                        key to when (value.classifier) {
                            Boolean::class.starProjectedType.classifier -> when (columns[key]) {
                                "true" -> true
                                "false" -> false
                                else -> false
                            }

                            Long::class.starProjectedType.classifier -> columns[key]
                            Int::class.starProjectedType.classifier -> columns[key]
                            String::class.starProjectedType.classifier -> columns[key]
                            Float::class.starProjectedType.classifier -> columns[key]
                            Double::class.starProjectedType.classifier -> columns[key]

                            else -> Gson().fromJson(columns[key].toString(), value.javaType)

                        }
                    }

            val json = Gson().toJson(newColumns)

            e{json}

            return Gson().fromJson(json, object : TypeToken<T>() {}.type)
        }

    }
}