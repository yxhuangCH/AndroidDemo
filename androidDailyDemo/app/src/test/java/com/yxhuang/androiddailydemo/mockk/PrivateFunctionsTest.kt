package com.yxhuang.androiddailydemo.mockk

import com.google.common.truth.Truth.assertThat
import io.mockk.*
import org.junit.Test

/**
 * Created by yxhuang
 * Date: 2022/7/5
 * Description:
 */
class PrivateFunctionsTest {

    class GenericsClass {
        private fun <T> updateItem(id: Long, name: String, date: T) {
            print("updateItem id $id, name $name")

        }

        fun pubCall() {
            updateItem(1L, "abc", "DATA")
        }
    }

    @Test
    fun testPrivateMethodWitGenericsClass() {
        val genericsClass = spyk<GenericsClass>()

        every {
            genericsClass["updateItem"](any<Long>(), any<String>(), any()) as Unit
        } just Runs

        genericsClass.pubCall()
    }


    class Abc {
        fun y() = x()

        private fun x() = "abc"

        fun a(genericsClass: GenericsClass) {
            z(genericsClass) {
                println(" it: $it")
            }
        }

        private fun z(genericsClass: GenericsClass, callback: (Boolean) -> Unit) {

        }
    }

    @Test
    fun testPrivateMethod() {
        val abc = spyk(Abc(), recordPrivateCalls = true)
        every { abc["x"]() } returns "def"

        val result = abc.y()

        assertThat(result).isEqualTo("def")
        verifySequence {
            abc.y()
            abc["x"]()
        }
    }

    @Test
    fun testPrivateMethodWithHigherOrderFunctionArg() {
        val abc = spyk(Abc(), recordPrivateCalls = true)
        val callback: (Boolean) -> Unit = mockk(relaxed = true)
        val genericsClass = mockk<GenericsClass>()

        justRun { abc invoke "z" withArguments listOf(genericsClass, callback)}

        abc.a(genericsClass)

        verifySequence {
            abc.a(genericsClass)
            abc["z"](any<GenericsClass>(), any<(Boolean) -> Unit>())
        }
    }

    @Test
    fun spyNoRecordingPrivateMethod() {
        val abc = spyk<Abc>()
        every { abc["x"]() } returns "def"

        val result = abc.y()

        assertThat(result).isEqualTo("def")
        verifySequence {
            abc.y()
        }
    }

    object Def {
        fun y() = x()
        private fun x() = "def"
    }

    // 测试 Object 私有函数
    @Test
    fun testObjectPrivateMethod() {
        mockkObject(Def, recordPrivateCalls = true)

        every { Def["x"]() } returns "ghi"

        val result = Def.y()

        assertThat(result).isEqualTo("ghi")
        verify {
            Def.y()
            Def["x"]()
        }
    }

    // 测试 Object 私有函数
    @Test
    fun testNoRecordingObjectPrivateMethod() {
        mockkObject(Def)

        every { Def["x"]() } returns "ghi"

        val result = Def.y()

        assertThat(result).isEqualTo("ghi")
        verify {
            Def.y()
        }
    }

    class MockClass {
        fun y(a: Int, b: Int?, def: Def?) = x(a, b, def)

        private fun x(a: Int, b: Int?, d: Def?) = "abc $a $b"
    }

    // 测试可空参数的私有函数
    @Test
    fun testPrivateCallsWithNullability() {
        val mockkClass = spyk<MockClass>(recordPrivateCalls = true)

        every { mockkClass["x"](any<Int>(), any<Int>(), any<Def>()) } returns "Test"

        val result = mockkClass.y(1, 3, null)

        assertThat(result).isEqualTo("Test")
        verify {
            mockkClass["x"](any<Int>(), any<Int>(), any<Def>())
        }
    }

    class PrivateNoReturnClass {
        fun myPublicMethod() {
            myPrivateMethod()
        }

        private fun myPrivateMethod() {

        }
    }

    @Test
    fun testPrivateMethodThatReturnsNoting() {
        val myClass = spyk<PrivateNoReturnClass>(recordPrivateCalls = true)
        every { myClass invokeNoArgs "myPrivateMethod" } returns Unit

        myClass.myPublicMethod()

        verify {
            myClass invokeNoArgs "myPrivateMethod"
        }
    }

    @Test
    fun justRunsWithPrivateMethod() {
        val mock = spyk<PrivateNoReturnClass>(recordPrivateCalls = false)
        justRun { mock invokeNoArgs "myPrivateMethod" }

        mock.myPublicMethod()
    }
}