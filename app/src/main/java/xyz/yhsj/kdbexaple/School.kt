package xyz.yhsj.kdbexaple

import xyz.yhsj.kdb.sqlite.annotation.PrimaryKey
import java.io.Serializable

/**
 * Created by LOVE on 2017/8/7 007.
 */
data class School(
        @PrimaryKey
        var id: Int? = null,
        var name: String = "",
        var student: List<UserData>? = null
) : Serializable