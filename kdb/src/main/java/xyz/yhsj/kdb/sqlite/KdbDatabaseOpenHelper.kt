package xyz.yhsj.kdb.sqlite

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import org.jetbrains.anko.db.ManagedSQLiteOpenHelper

/**
 * 数据库帮助类
 * Created by LOVE on 2017/8/4 004.
 */


class KdbDatabaseOpenHelper(ctx: Context, name: String, factory: SQLiteDatabase.CursorFactory?, version: Int) : ManagedSQLiteOpenHelper(ctx, name, factory, version) {
    override fun onCreate(db: SQLiteDatabase?) {

    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {

    }

    companion object {
        private var instance: KdbDatabaseOpenHelper? = null

        @Synchronized
        fun getInstance(ctx: Context, databaseConfig: KdbConfig): KdbDatabaseOpenHelper {
            if (instance == null) {
                instance = KdbDatabaseOpenHelper(ctx.applicationContext, databaseConfig.name, databaseConfig.factory, databaseConfig.version)
            }
            return instance!!
        }
    }

}
