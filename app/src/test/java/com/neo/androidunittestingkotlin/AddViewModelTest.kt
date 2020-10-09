package com.neo.androidunittestingkotlin

import com.neo.androidunittestingkotlin.add.AddViewModel
import com.neo.androidunittestingkotlin.data.TodoRepository
import com.nhaarman.mockitokotlin2.*
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Test

class AddViewModelTest {

    @Test
    fun testSaveWithoutDate(){
        // test for saving to db with values of viewModel gotten from AddActivity
        val repository : TodoRepository = mock()
        val model = AddViewModel(repository)

        val actualTitle = "Test todo"
        model.todo.title = actualTitle

        // act(this function ret null if no issue else ret something)
        val actual = model.save()

        assertNull(actual)

        verify(repository).insert(
            argThat {
                title == actualTitle && dueDate == null
            }
        )
    }

    @Test
    fun testSaveWithDate(){
        // test for saving to db with values of viewModel gotten from AddActivity
        val repository : TodoRepository = mock()
        val model = AddViewModel(repository)
        val actualTitle = "Test todo"
        model.todo.title = actualTitle
        val actualDate = System.currentTimeMillis()
        model.todo.dueDate = actualDate

        // act(this function ret null if no issue else ret something)
        val actual = model.save()

        assertNull(actual)

        // confirms the repo insert fun was called with condition in argThat
        verify(repository).insert(
            argThat {
                title == actualTitle && dueDate == actualDate
            }
        )
    }

    @Test
    fun testSaveNoTitle(){
        // test for saving to db with values of viewModel gotten from AddActivity
        val repository : TodoRepository = mock()
        val model = AddViewModel(repository)
        val expected = "Title is required"

        // act(this function ret null if no issue else ret something)
        val actual = model.save()

        assertEquals(expected, actual)
        // verifies that repo insert() fun was never called
        verify(repository, never()).insert(any())
    }
}