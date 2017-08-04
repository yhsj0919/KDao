package xyz.yhsj.kdbexaple

import xyz.yhsj.kdb.sqlite.annotation.PrimaryKey
import java.io.Serializable

/**
 * 数据实体,必须实现Serializable序列化
 * Created by LOVE on 2017/8/4 004.
 */
data class UserData(
        @PrimaryKey
        var id: Int? = null,
        var name: String = "",
        var age: Int = -1,
        var isChild: Boolean = false
) : Serializable