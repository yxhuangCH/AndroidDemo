package com.yxhuang.androiddailydemo.reflect

import kotlin.reflect.KMutableProperty1
import kotlin.reflect.full.*
import kotlin.reflect.jvm.isAccessible

/**
 * Created by yxhuang
 * Date: 2021/7/30
 * Description:
 */

//定义注解
annotation class Anno

@Deprecated("该类已经不推荐使用")
@Anno
class ReflectA(val name: String) {

    companion object{
        const val TAG = "ReflectA"
        fun show(){

        }
    }

    var age: Int = 0

    constructor() : this("ReflectA_")

    constructor(name: String, age: Int) : this(name) {
        this.age = age
    }

    fun print(str: String) {
        println("ReflectA print str $str")
    }

    fun sayHi(): String {
        println("ReflectA sayHi")
        return "sayHi"
    }

    class InnerClass
}

// 拓展方法
fun ReflectA.exfun() {
    println("exfun")
}

// 拓展属性
val ReflectA.foo: Double
    get() = 3.14


fun main() {
    println("Hello word")

    val clazz = ReflectA::class
    println(clazz)

    println("ReflectA 的全部构造器如下：")
    clazz.constructors.forEach {
        println(it)
    }

    println("ReflectA 的主构造器如下：")
    println(clazz.primaryConstructor)

    println(" ")
    //通过functions属性获取该KClass对象所对应类的全部方法
    val funs = clazz.functions
    println("ReflectA 的全部方法如下：")
    funs.forEach { println(it) }

    println(" ")
    //通过 declaredFunctions 属性获取该KClass对象声明的全部方法
    val funs2 = clazz.declaredFunctions
    println("ReflectA 本身声明的全部方法如下：")
    funs2.forEach { println(it) }

    println(" ")
    //通过 memberExtensionFunctions 属性获取全部扩展方法
    val exetensionFunctions = clazz.memberExtensionFunctions
    println("ReflectA 声明的扩展方法如下：")
    exetensionFunctions.forEach { println(it) }

    println(" ")
    //通过decaredMemberProperties获取全部成员属性
    var memberProperties = clazz.declaredMemberProperties
    println("ReflectA 本身声明的成员属性如下：")
    memberProperties.forEach { println(it) }

    println(" ")
    //通过memberExtensionProperties属性获取该KClass对象的全部扩展属性
    var exProperties = clazz.memberExtensionProperties
    println("ReflectA 本身声明的扩展属性如下：")
    exProperties.forEach { println(it) }

    println(" ")
    //通过annotations属性获取该KClass对象所对应类的全部注解
    val anns = clazz.annotations
    println("ReflectA 的全部注解如下：")
    anns.forEach { println(it) }
    println("该KClass元素上的@Annot注解为：${clazz.findAnnotation<Anno>()}")

    println(" ")
    //通过nestedClasses属性获取所对应的全部嵌套类
    val inners = clazz.nestedClasses
    println("ReflectA 的全部内部类如下：")
    inners.forEach { println(it) }

    println(" ")
    //通过supertypes属性获取该类的所有父类型
    println("KClassTest的父类型为：${clazz.supertypes}")


    println(" ")
    println("---------- companion 对象 ---------") //
    val companion = clazz.companionObject // 返回也是一个 KClass
    if (companion != null){
        println("companion $companion")
        companion.declaredMemberProperties.forEach {
            println("companion declaredMemberProperties:  $it")
        }
        companion.declaredFunctions.forEach {
            println("companion declaredFunctions:  $it")
        }
    }


    println(" ")


    println("---------- 创建对象 ---------")
    println(" ")
    println("createInstance 创建实例")
    // createInstance() 方法调用无参数的构造器创建实例
    val inst2 = clazz.createInstance()
    println(inst2.name)
    println(inst2.age)

    println(" ")
    // primaryConstructor 主构造函数
    val cons1 = clazz.primaryConstructor
    val inst1 = cons1?.call("hello reflect")  // 参入参数
    println(inst1)
    println("inst1 " + inst1?.name)

    println(" ")
    println("第一个构造函数")
    val cons2 = clazz.constructors.first()
    println(cons2)

    println(" ")

    println("-------调用方法------")
    val funs3 = clazz.declaredFunctions
    val inst3 = clazz.createInstance()
    println("ReflectA 本身声明的全部方法如下：")
    funs3.forEach { println(it) }
    for (f in funs3) {
        if (f.name == "sayHi") {
            f.call(inst3)
        }
        if (f.name == "print") {
            f.call(inst3, "反射打印")
        }
    }

    println("\n")
    println("-------访问属性------")
    //通过decaredMemberProperties获取全部成员属性
    val memberProperties2 = clazz.declaredMemberProperties
    val inst4 = clazz.createInstance()
    println("ReflectA 本身声明的成员属性如下：")
    memberProperties2.forEach { println(it) }
    println("inst4 name: ${inst4.name}")
    memberProperties2.forEach {
        if (it.name == "age") {
            it as KMutableProperty1<ReflectA, Int>
            it.isAccessible = true
            it.set(inst4, 20)
            println(it.get(inst4))
        }

    }

}