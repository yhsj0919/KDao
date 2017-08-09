package xyz.yhsj.kdaoexample

import android.content.Intent
import android.support.design.widget.Snackbar
import android.support.v7.widget.Toolbar
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*
import org.jetbrains.anko.db.SqlOrderDirection
import xyz.yhsj.kdao.sqlite.condition
import xyz.yhsj.kdao.sqlite.operator.*
import java.util.*
import kotlin.collections.ArrayList

class MainActivity : BaseActivity() {
    /**
     * 返回布局id
     */
    override fun getLayoutId(): Int = R.layout.activity_main

    /**
     * 返回toolbar
     */
    override fun getToolBar(): Toolbar? = toolbar

    /**
     * 初始化功能
     */
    override fun init() {
        supportActionBar!!.setDisplayHomeAsUpEnabled(false)

        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()

            val juser = JavaUser("名字", 10, true)

            juser.thisIgnore = "这是忽略的"

            juser.save()

            val jusers = JavaUser().findAll { }

            jusers.forEach {

                println(it)
            }


        }

        btnSave.setOnClickListener {
            save()
        }

        btnAll.setOnClickListener {
            saveAll()
        }

        btnFind.setOnClickListener { find() }

        btnUpdate.setOnClickListener { update() }

        btnDelete.setOnClickListener { delete() }

        btnOneMore.setOnClickListener {
            School(name = "测试包含对象", student = (1..10).map { UserData(name = "批量$it", age = it, isChild = it % 2 == 0) }).save()
            val school = School().findOneByKey(1)
            println(school)
        }
    }

    /**
     * 保存
     */
    private fun save() {
        //save
        val user = UserData()
        user.id = 3
        user.name = "sen"
        user.age = 23
        user.isChild = false
        println(user.save(true))
    }

    /**
     * 保存列表
     */
    private fun saveAll() {

        val list = (1..1000).map { UserData(name = "批量$it", age = it, isChild = it % 2 == 0) }

        val time = Date().time

        println("开始时间" + time)
        list.saveAll(replace = true)
        println("耗时" + (Date().time - time))
    }


    private fun find() {
        val user1: UserData? = UserData().findOneByKey(33)
        e { "$user1" }

        val user2 = UserData().findOne {
            condition {
                "age" between 7..16
                "isChild" Not false
            }
            orderBy("age", SqlOrderDirection.ASC)
        }
        e { user2?.name ?: "no user witch age is between 7..16 and isChild is not false" } //批量8

        val userList = UserData().findAll {}
        e { "${userList.size}" } // 1000

    }

    private fun update() {
        //update
        var user3 = UserData().findOne {
            condition { "name" equalsData "批量3" }
        }
        if (user3 != null) {
            user3.age += 1
            user3.update()
        }

        user3 = UserData().findOne { condition { "name" equalsData "批量3" } }
        e { "${user3?.age}" }

        //or
        var user4: UserData? = UserData(3, "lucy", 10, true)//user4.id == user3.id · 注意primary key相同
        user4?.update()//user4 will overwrite the old data · 将会覆盖掉旧数据
        user4 = UserData().findOneByKey(3)
        e { user4?.age.toString() } // 10


        var user5: UserData? = UserData(90, "white", 77, false)// 90 is a new primary key
        user5?.updateOrSave()// KDao will save a new data
        user5 = UserData().findOneByKey(90)
        e { user5?.name.toString() } // "white"


        UserData().updateByKey(90) { -> arrayOf("name" to "black", "age" to 80) }
        val user6: UserData? = UserData().findOneByKey(90)
        e { user6?.name + ":" + user6?.age } //white:77->black:80

        UserData().updateByKey(90, "name" to "green", "age" to 82)
        val user7: UserData? = UserData().findOneByKey(90)
        e { user7?.name + ":" + user7?.age } //black:80->green:82

        UserData().update("name" to "red", "age" to 99) {
            condition {
                "name" equalsData "green"
            }
        }
        val user8: UserData? = UserData().findOneByKey(90)
        e { user8?.name + ":" + user8?.age } //green:82->red:99

    }

    private fun delete() {

        //deleteAll
        var user9: UserData? = UserData(90)//only need primary key
        user9?.delete()
        user9 = UserData().findOneByKey(90)
        e { user9?.name ?: "no user" }//no user
//
        UserData().deleteByKey(666)//deleteAll sen
        val user10 = UserData().findOneByKey(666)
        e { user10?.name ?: "no user" }//no user
//
        UserData().deleteAll {
            condition {
                "isChild" equalsData true
            }
        }//john and lucy will be deleted
        val children = UserData().findAll { condition { "isChild" equalsData true } }
        e { children.size.toString() }//0

        e { UserData().findAll { }.size.toString() }//1
        UserData().clear()
        e { UserData().findAll {}.size.toString() }//0

    }
}
