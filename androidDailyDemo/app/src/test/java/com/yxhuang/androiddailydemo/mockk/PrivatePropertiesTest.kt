package com.yxhuang.androiddailydemo.mockk

import android.app.Person
import com.google.common.truth.Truth.assertThat
import io.mockk.*
import org.junit.Test

/**
 * Created by yxhuang
 * Date: 2022/7/6
 * Description:
 */
class PrivatePropertiesTest {

    data class Person(var name: String)

    class Team {
        protected var person: Person = Person("Init")
            get() = Person("Ben")
            set(value) {
                field = value
                print("set person value $value")
            }

        protected fun fn(arg: Int): Int = arg + 5
        fun pubFn(arg: Int) = fn(arg)

        var memberName: String
            get() = person.name
            set(value) {
                person = Person(value)
                print("set memberName value $value")
            }
    }

    @Test
    fun testPrivateProperty() {
        val team = spyk(Team(), recordPrivateCalls = true)

        every { team getProperty "person" } returns Person("Big Ben")
        every { team setProperty "person" value Person("test")} just Runs
        every { team invoke "fn" withArguments listOf(5) } returns 3

        val memberNameValue = team.memberName
        assertThat(memberNameValue).isEqualTo("Big Ben")

        val fnResult = team.pubFn(5)
        assertThat(fnResult).isEqualTo(3)

        team.memberName = "test"
        assertThat(team.memberName).isEqualTo("Big Ben")

        verify { team getProperty "person" }
        verify { team setProperty  "person"  value Person("test")}
        verify { team invoke "fn" withArguments listOf(5) }
    }
}
