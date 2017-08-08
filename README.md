[![GitHub license](https://img.shields.io/badge/license-Apache%20License%202.0-blue.svg?style=flat)](http://www.apache.org/licenses/LICENSE-2.0)
# Kdb

#Anko sqlite扩展,通过反射直接创建表,结合Gson进行数据的反解析

#项目参考修改了https://github.com/senyuyuan/Gluttony  对存储,数据解析做了一定的改变

## Content · 目录

* [Feature · 特性](#feature--特性)
* [How to use · 如何使用](#how-to-use--如何使用)
	* [Configuration · 配置](#configuration--配置)
	* [Entities · 实体](#entities--实体)
	* [Save · 保存](#save--保存)
	* [Find · 查询](#find--查询)
	* [Update · 更新](#update--更新)
	* [Delete · 删除](#deleteAll--删除)
	* [Condition · 条件](#condition--条件)
 

## Feature · 特性
```
* 核心理念，对人类友好,Kdb将会给您完美的使用体验
```
```
* 通过反射自动建表
```
```
* 实体类无需任何处理 (实现Serializable序列化)
```

## How to use · 如何使用

### Configuration · 配置

```kotlin
        //配置 数据库名称，cursorFactory，数据库版本
         Kdb.init(this, KdbConfig("kdb_example", null, 1))
```

在Application或是首个Activity中，初始化Kdb。

### Entities · 实体
实体类无需做任何处理。
Kdb在数据库中自动为您打理好一切。

注解：@PrimaryKey 用来指定主键。@Ignore用于忽略属性
```kotlin
data class UserData(
        @PrimaryKey
        var id: Int? = null,
        var name: String = "",
        var age: Int = -1,
        var isChild: Boolean = false,
        @Ignore
        var thisIgnore: String = "thisIgnore"
) : Serializable
```


## Save · 保存
### Save Entity directly · 直接保存实体
默认主键自增,主键字段可以为空
参数为是否替换主键相同的记录
```kotlin
         val user = UserData()
                user.id = 3
                user.name = "sen"
                user.age = 23
                user.isChild = false
                user.save(true)

        //or
        UserData(2, "john", 12, true).save()
```
### Save List Entity· 保存实体列表
```kotlin
        val list = (1..1000).map { UserData(name = "批量$it", age = it, isChild = it % 2 == 0, thisIgnore = "这个是忽略的属性$it") }
        
        list.saveAll(replace = true)
                
```
### Save one to more Entity   one to one· 直接一对多,一对一对象
注:子对象暂时不支持查询(内部通过转换json实现)
```kotlin
         School(name = "测试包含对象", student = (1..10).map { UserData(name = "批量$it", age = it, isChild = it % 2 == 0) }).save()
                
```

## Find · 查询
### Find Entity based on PrimaryKey · 根据primary key 查询数据
```kotlin
        val user1 = UserData().findOneByKey(666)
```

### Find the first Entity based on Condition · 根据条件 查询第一个数据
```kotlin
        val user2 = UserData().findOne {
            condition {
                "age" between 7..16
                "isChild" Not false
            }
            orderBy("age", SqlOrderDirection.ASC)
        }
```

### Find all Entities based on Condition · 根据条件 查询所有数据，返回值为一个列表
```kotlin
        val userList = UserData().findAll {
            condition {
                "age" moreThan 11
                "name" like "s%"	//find names witch is starting with "s"
            }
        }
```

## Update · 更新

### Update Entity directly base on PrimaryKey · 根据primary key 直接更新实体
```kotlin
	var user3 = UserData().findOne { condition { "name" equalsData "lucy" } }!!
        user3.age += 1
        user3.update()
        
        //or
        
        var user4 = UserData(7, "lucy", 10, true)	//user4.id == user3.id · 注意primary key相同
        user4.update()	//user4 will overwrite the old data · 将会覆盖掉旧数据
```

### Update Entity directly or Save Entity when it doesn't exist · 直接 更新或保存实体 （如果实体是未保存过的话）
```kotlin
        var user5 = UserData(90, "white", 77, false)	// 90 is a new primary key
        user5.updateOrSave()	// Kdb will save a new data
```

### Update Entity based on PrimaryKey · 根据primary key 更新实体
```kotlin
	//update user who id is 90 to named black,age 80
	//lambda
        UserData().updateByKey(90) { arrayOf("name" to "black", "age" to 80) }

        //or pairs
        UserData().updateByKey(90, "name" to "green", "age" to 82)
```

### Update all Entities based on Condition · 根据条件 更新所有实体
```kotlin
	//update user who name is green to name red,age 99
        UserData().update("name" to "red", "age" to 99) {
            condition {
                "name" equalsData "green"
            }
        }
```

## Delete · 删除

### Delete Entity directly · 直接删除实体
```kotlin
        var user9 = UserData(90)	//only need primary key
        user9.deleteAll()
```


### Delete Entity based on PrimaryKey · 根据primary key 删除实体
```kotlin
        UserData().deleteByKey(666)	//deleteAll user which id is 666
```

### Delete all Entities based on Condition · 根据条件 删除所有实体
```kotlin
	//deleteAll users who is child
        UserData().deleteAll {
            condition {
                "isChild" equalsData true
            }
        }
```

### Clear one Class's entities · 清空一个类的所有实体
```kotlin
        UserData().clear()
```

## Condition · 条件

* equalsData · 等于

* moreThan · 大于

* moreThanOrEquals · 大于等于

* lessThan · 小于

* lessThanOrEquals · 小于等于

* Not · 非

* In:		Determine whether in the array · 判断是否在数组中 

* notIn:	Determine whether not in the array

* between:	Determine whether in the range

* like:		fuzzy query · 模糊查询

```

two marks: % and _ · 两个通配符：% 和 _

% : indefinite amount content · 不定数量的内容

_ : one amount content · 一个位置的内容

for example:	"Kdb%" -> find values witch is starting with "Kdb"

例如，"Kdb%" -> 查询所有以"Kdb"开头的数据

```
