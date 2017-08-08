package xyz.yhsj.kdao.sqlite

import android.util.Log
import xyz.yhsj.kdao.Kdao

/**日志
 * Created by LOVE on 2017/8/4 004.
 */


fun Any.e(tag: String, message: String) {
    if (Kdao.isDebug.not()) return
    Log.e(tag, message)
}

fun Any.e(tag: String, functor: () -> String) = e(tag, functor())

fun Any.e(functor: () -> String) = e(this.javaClass.simpleName, functor)
