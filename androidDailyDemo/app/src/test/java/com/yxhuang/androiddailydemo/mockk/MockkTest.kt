package com.yxhuang.androiddailydemo.mockk

import com.google.common.truth.Truth.assertThat
import com.yxhuang.androiddailydemo.mockk.MyObject.extFun3
import io.mockk.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Test

/**
 * Created by yxhuang
 * Date: 2022/7/4
 * Description:
 */
class MockkTest {


    @Before
    fun setup() {
        MockKAnnotations.init(this)
    }

    // 测试 spy
    @Test
    fun testSpy() {
        val engine = Engine()
        val car = spyk(Car(engine))
        car.drive(Direction.NORTH)
        verify { car.drive(Direction.NORTH) }
    }

    //  测试 Partial mocking
    @Test
    fun testPartialMocking() {
        val adder = mockk<Adder>() // 调用 Adder 的默认构造函数

        every { adder.addOne(any()) } returns -1
        every { adder.addOne(3) } answers { callOriginal() }

        val result1 = adder.addOne(2)
        assertThat(result1).isEqualTo(-1)

        val result2 = adder.addOne(3)
        assertThat(result2).isEqualTo(4)
    }

    // 测试私有方法
    @Test
    fun testPrivateCalSpeed() {
        val engine = spyk<Engine>(recordPrivateCalls = true)
        val car = Car(engine)

        // 调用私有函数 calSpeed()
        every { engine["calSpeed"]() } returns 20
        val speed = car.getSpeed()

        assertThat(speed).isEqualTo(20)

        // 反射调用私有函数 setName()
        InternalPlatformDsl.dynamicCall(engine, "setName", arrayOf("test name")) {
            mockk()
        }

        // 反射获取私有属性 name
        val name = InternalPlatformDsl.dynamicGet(engine, "name")
        println("dynamicGet name: $name ")
        assertThat(name).isEqualTo("test name")

        // 反射设置私有属性 name
        InternalPlatformDsl.dynamicSet(engine, "name", "name2")
        // 反射调用私有函数 getName
        val name2 = InternalPlatformDsl.dynamicCall(engine, "getName", arrayOf()) { mockk() }
        assertThat(name2).isEqualTo("name2")

        // 验证私有函数被调用
        car.getSpeed()
        verify { engine.invoke("setName").withArguments(arrayListOf("calSpeed test")) }
    }

    // 测试 capture
    @Test
    fun testCapturing() {
        val engine = Engine()
        val car = spyk(Car(engine))

        val slotDirection = slot<Direction>()
        every { car.drive(capture(slotDirection)) } just runs

        car.drive(Direction.NORTH)

        assertThat(slotDirection.captured).isEqualTo(Direction.NORTH)
    }

    // 测试协程
    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun testCoroutine() = runBlockingTest {
        val engine = Engine()
        val car = spyk(Car(engine))

        coEvery { car.start() } coAnswers { callOriginal() }

        val startResult = car.start()

        assertThat(startResult).isTrue()
    }

    // 测试拓展函数 Class 层级
    @Test
    fun testExtensionFun() {
        val car = mockk<Car>()
        every { car.getSpeed() } answers { callOriginal() }

        with(mockk<Ext>()) {
            every { car.extFun() } returns 4
            val extFunResult = car.extFun()
            assertThat(extFunResult).isEqualTo(4)
            verify { car.extFun() }
        }
    }

    // 测试拓展函数 module 层级
    @Test
    fun testExtensionFun2() {
        val car = mockk<Car>()
        every { car.getSpeed() } answers { callOriginal() }
        every { car.extFun2() } returns 5

        mockkStatic("com.yxhuang.androiddailydemo.mockk.CarKt")
        val extFunResult = car.extFun2()

        assertThat(extFunResult).isEqualTo(6)
        verify { car.extFun2() }
    }

    // 测试拓展函数 Object 层级
    @Test
    fun testExtensionFun3() {
        val car = mockk<Car>()
        every { car.getSpeed() } answers { callOriginal() }

        mockkObject(MyObject)
        with(MyObject) {
            every { car.extFun3() } returns 6
        }
        val extFunResult = car.extFun3()

        assertThat(extFunResult).isEqualTo(6)
        verify { car.extFun3() }
    }

    @Test
    fun testPrivateFunction() {
        val car = spyk(Car(Engine()), recordPrivateCalls = true)
        every { car["accelerate"]() } returns "going not so fast"

        val startAccelerateResult = car.startAccelerate()

        assertThat(startAccelerateResult).isEqualTo("going not so fast")

        verifySequence {
            car.startAccelerate()
            car["accelerate"]()
        }
    }

}