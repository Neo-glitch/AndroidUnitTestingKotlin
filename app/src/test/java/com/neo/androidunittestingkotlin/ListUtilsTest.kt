package com.neo.androidunittestingkotlin

import com.neo.androidunittestingkotlin.list.determineCardColor
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.Parameterized


/**
 * the parametrized::class lets Junit to use parameterised test runner when running test class
 */
@RunWith(Parameterized::class)
class ListUtilsTest(
    private val expected: Int,
    private val dueDate: Long?,
    private val done: Boolean,
    private val scenario: String
) {

    //  companion objects ties members(var / functions) to a class and members directly using the class.
    companion object{
        val now = System.currentTimeMillis()
        val day = 1000 * 60 * 60 * 24

        // creates list of params to send to test
        @JvmStatic
        @Parameterized.Parameters(name = "determineCardColor: {3}")  // makes each test to be labelled with sting in () and passed value of 4th element in each item(i.e scenario value here)
        fun todos() = listOf(
            // array used to run test multiple(here is 3) times with diff params
            arrayOf(R.color.todoDone, null, true, "Done, no date"),
            arrayOf(R.color.todoNotDue, null, false, "Not Done, no date"),
            arrayOf(R.color.todoOverDue, now - day, false, "Not Done, due yesterday")
        )

    }


    @Test
    fun testDetermineCardColor(){
//        val expected = R.color.todoDone
//        val dueDate = null
//        val done = true

        // act of the test, and calls the fun from ListUtil class
        val actual = determineCardColor(dueDate, done)

        assertEquals(expected, actual)
    }

}