package com.yxhuang.androiddailydemo.mockk

import com.google.common.truth.Truth.assertThat
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.verify
import org.junit.Before
import org.junit.Test

/**
 * Created by yxhuang
 * Date: 2022/7/6
 * Description:
 */
class NullsTest {

    interface Wrapper

    data class IntWrapper(val date: Int) :Wrapper

    class MockClass{

        fun function(a: Wrapper?, b: Wrapper?): Int?{
            return if (a is IntWrapper && b is IntWrapper){
                a.date + b.date
            } else {
                0
            }
        }
    }

    @MockK
    lateinit var mock: MockClass

    @Before
    fun setup(){
        MockKAnnotations.init(this)
    }

    @Test
    fun isNull(){
        every { mock.function(null, isNull()) } returns 4

        assertThat(mock.function(null, null)).isEqualTo(4)

        verify { mock.function(isNull(), null) }
    }

    @Test
    fun returnsNull(){
        every { mock.function(IntWrapper(1), IntWrapper(1)) } returns null

        assertThat(mock.function(IntWrapper(1), IntWrapper(1))).isEqualTo(null)

        verify { mock.function(IntWrapper(1), IntWrapper(1)) }
    }
}
