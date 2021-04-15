//package com.yxhuang.androiddailydemo.serializa
//
//import kotlinx.serialization.Serializable
//import kotlinx.serialization.*
//import kotlinx.serialization.json.*
//
///**
// * Created by yxhuang
// * Date: 2021/3/6
// * Description: 序列化的类
// */
////@Serializable
//sealed class Project(val name: String)
//
////@Serializable
//class SubProject(name: String, val owner: String) : Project(name)
//
////@Serializable
//sealed class Message(){
//
//    abstract val content:String
//
//}
//
////@Serializable
//data class BroadcastMessage(override val content: String):Message()
//
////@Serializable
//data class DirectMessage(override val content: String, val name:String):Message()
//
//
//
////fun main() {
////    val data = Project("kotlinx.serialization")
////    println(Json.encodeToString(data))
////
////    val list = arrayListOf<Message>(
////            BroadcastMessage("heill"),
////            DirectMessage("daaa", "dddon")
////    )
////    list.forEach {
////        println(it)
////    }
////
////    println("------")
////    val listString = Json.encodeToString(list)
////    println(listString)
////    println("------")
////    val obj = Json.decodeFromString<List<Message>>(listString)
////    for (item in obj) {
////        println("----item $item")
////    }
////
////    println("------")
////    val data2 = SubProject("kotlinx.coroutines", "kotlin")
////    println(Json.encodeToString(data2))
////
////
////}