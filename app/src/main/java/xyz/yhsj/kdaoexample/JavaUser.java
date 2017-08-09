package xyz.yhsj.kdaoexample;

import org.jetbrains.annotations.NotNull;

import java.io.Serializable;

import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import xyz.yhsj.kdao.sqlite.annotation.Ignore;
import xyz.yhsj.kdao.sqlite.annotation.PrimaryKey;

/**
 * Created by LOVE on 2017/8/8 008.
 */

public class JavaUser implements Serializable {
    @PrimaryKey
    private Integer Id;
    private String name;
    private int age;
    private boolean isChild;
    @Ignore
    private String thisIgnore;

    public JavaUser() {
    }

    public JavaUser(String name, int age, boolean isChild) {
        this.name = name;
        this.age = age;
        this.isChild = isChild;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public boolean isChild() {
        return isChild;
    }

    public void setChild(boolean child) {
        isChild = child;
    }

    public String getThisIgnore() {
        return thisIgnore;
    }

    public void setThisIgnore(String thisIgnore) {
        this.thisIgnore = thisIgnore;
    }

    @Override
    public String toString() {
        return "JavaUser{" +
                "Id=" + Id +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", isChild=" + isChild +
                ", thisIgnore='" + thisIgnore + '\'' +
                '}';
    }
}
