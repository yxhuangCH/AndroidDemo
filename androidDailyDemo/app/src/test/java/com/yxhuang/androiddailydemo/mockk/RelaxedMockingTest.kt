package com.yxhuang.androiddailydemo.mockk

import com.google.common.truth.Truth.assertThat
import io.mockk.MockKException
import io.mockk.every
import io.mockk.mockk
import junit.framework.Assert.assertEquals
import org.junit.Test
import kotlin.test.assertFailsWith

/**
 * Created by yxhuang
 * Date: 2022/7/6
 * Description:
 */
class RelaxedMockingTest {

    class MockClass {

        fun option(a: Int, b: Int) = a + b

        fun optionUnit(a: Int, b: Int) {

        }
    }

    @Test
    fun testRegularOptionOk() {
        val mock = mockk<MockClass>(relaxUnitFun = true){
            every { option(1, 2) } returns 4
        }

        val result = mock.option(1, 2)

        assertThat(result).isEqualTo(4)
    }

    @Test
    fun testRegularOptionFail(){
        val mock = MockClass()

        assertFailsWith<MockKException> {
            assertEquals(4, mock.option(1, 2))
        }
    }
}
