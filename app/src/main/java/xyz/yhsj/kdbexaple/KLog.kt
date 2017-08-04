package xyz.yhsj.kdbexaple

import android.util.Log

/**
 * Created by LOVE on 2017/8/4 004.
 */
fun e(tag: String, message: String) {
    Log.e(tag, message)
}

fun e(tag: String, functor: () -> String) = e(tag, functor())

fun Any.e(functor: () -> String) = e(this.javaClass.simpleName, functor)
