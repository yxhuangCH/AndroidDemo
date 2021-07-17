package com.yxhuang.kotlinpoet

import Greeter
import com.squareup.kotlinpoet.*
import java.io.File

/**
 * Created by yxhuang
 * Date: 2021/7/17
 * Description:
 */
class KotlinPoetClient {

}

fun main() {
    generateKt()

    Greeter("koltin poet").greet()
}

private fun generateKt(){

//    // 创建 类
//    val helloWord = ClassName("com.yxhuang.kotlinpoet", "HelloWord" )
//    // 构造函数
//    val constructorBuilder = FunSpec.constructorBuilder()
//        .addParameter("name", String::class.java)
//        .build()
//    // 函数
//    val sayHi = FunSpec.builder("sayHi")
//        .addStatement("println(%P)", "Hello, \$name")
//        .build()
//
//    val file = FileSpec.builder("com.yxhuang.kotlinpoet", "HelloWord")
//        .addFunction(sayHi)
//        .build()

    val greeterClass = ClassName("", "Greeter")

    // 构造函数
    val primaryConstructor = FunSpec.constructorBuilder()
        .addParameter("name", String::class)
        .build()
    // 创建属性
    val nameFile = PropertySpec.builder("name", String::class)
        .initializer("name")
        .build()

    // 创建函数
    val greet = FunSpec.builder("greet")
        .addStatement("println(%P)", "Hello, \$name")
        .build()

    val file = FileSpec.builder("", "HelloWorld")
        .addType(TypeSpec.classBuilder("Greeter")
            .primaryConstructor(primaryConstructor)
            .addProperty(nameFile)
            .addFunction(greet)
            .build())
        .build()
    file.writeTo(File("kotlinpoet/src/main/java/"))


}