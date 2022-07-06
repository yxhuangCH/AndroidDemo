package com.yxhuang.androiddailydemo.mockk

import kotlinx.coroutines.delay

/**
 * Created by yxhuang
 * Date: 2022/7/4
 * Description:
 */
class Car(private val engine: Engine) {

    fun getSpeed(): Int {
        return engine.getSpeed()
    }

    fun drive(direction: Direction) {
        print("Car drive direction: $direction")
    }

    suspend fun start(): Boolean {
        delay(400)
        print("star delay")
        return true
    }

    fun startAccelerate() = accelerate()

    private fun accelerate() = "going faster"


}


class Engine {

    private var name: String? = null

    fun getSpeed(): Int {
        return calSpeed()
    }

    private fun calSpeed(): Int {
        return 30
    }

    private fun setName(name: String) {
        this.name = name
    }

    private fun getName(): String? {
        return this.name
    }

    private fun getSize(index: Int): Int {
        return if (index <= 5) {
            10
        } else {
            20
        }
    }

    private fun start(justNow: Boolean): String {
        return if (justNow) {
            "success"
        } else {
            "fail"
        }

    }
}

enum class Direction {
    NORTH
}

class Ext {
    //Class 层级的拓展
    fun Car.extFun() = getSpeed() + 1
}

// module 层级的拓展
fun Car.extFun2() = getSpeed() + 1

// Object 层级的拓展
object MyObject {
    fun Car.extFun3() = getSpeed() + 1
}