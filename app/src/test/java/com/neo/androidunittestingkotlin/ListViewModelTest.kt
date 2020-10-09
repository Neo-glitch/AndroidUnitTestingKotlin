package com.neo.androidunittestingkotlin

import androidx.lifecycle.MutableLiveData
import com.neo.androidunittestingkotlin.data.Todo
import com.neo.androidunittestingkotlin.data.TodoRepository
import com.neo.androidunittestingkotlin.list.ListViewModel
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.ExpectedException
import java.lang.IllegalArgumentException

class ListViewModelTest {

    @Rule
    @JvmField
    val expectedException = ExpectedException.none()    // rule tells junit not to expect exception by default

    val now = System.currentTimeMillis()
    val day = 1000 * 60 * 60 * 24

    @Test
    fun testAllTodosEmpty(){
        val expected = 0
        val repository:TodoRepository = mock()    // mocked todoRepository

        // tells mockito how to respond to calls from the mocked obj
        whenever(repository.getAllTodos())
            .thenReturn(MutableLiveData(arrayListOf()))

        // init the ListViewModel with the mocked repo
        val model = ListViewModel(repository)

        // act, gets list of todos from repo
        val todos = model.allTodos.value

        assertNotNull(todos)
        assertEquals(expected, todos!!.size)
    }


    @Test
    fun testAllTodosSingle(){
        val expected = 1
        val repository:TodoRepository = mock()    // mocked todoRepository

        // tells mockito how to respond to calls from the mocked obj
        whenever(repository.getAllTodos())
            .thenReturn(MutableLiveData(arrayListOf(
                Todo("5", "Todo 5", now + day, false, now)
            )))

        // init the ListViewModel with the mocked repo
        val model = ListViewModel(repository)

        // act, gets list of todos from repo
        val todos = model.allTodos.value

        assertNotNull(todos)
        assertEquals(expected, todos!!.size)
    }


    @Test
    fun testAllTodosMultiple(){
        val expected = 3
        val repository:TodoRepository = mock()    // mocked todoRepository

        // tells mockito how to respond to calls from the mocked obj
        whenever(repository.getAllTodos())
            .thenReturn(MutableLiveData(arrayListOf(
                Todo("5", "Todo 5", now + day, false, now),
                Todo("4", "Todo 4", now + day, false, now),
                Todo("3", "Todo 3", now + day, false, now)
            )))

        // init the ListViewModel with the mocked repo
        val model = ListViewModel(repository)

        // act, gets list of todos from repo
        val todos = model.allTodos.value

        assertNotNull(todos)
        assertEquals(expected, todos!!.size)
    }

    @Test
    fun testUpComingTodosCountEmpty(){
        val expected = 0
        val repository: TodoRepository = mock()
        whenever(repository.getUpcomingTodosCount())
            .thenReturn(MutableLiveData(expected))

        val model = ListViewModel(repository)
        val count = model.upcomingTodosCount.value

        assertNotNull(count)
        assertEquals(expected, count)
    }

    @Test
    fun testUpComingTodosCountSingle(){
        val expected = 1
        val repository: TodoRepository = mock()
        whenever(repository.getUpcomingTodosCount())
            .thenReturn(MutableLiveData(expected))
        val model = ListViewModel(repository)

        val count = model.upcomingTodosCount.value
        assertNotNull(count)
        assertEquals(expected, count)
    }

    @Test
    fun testUpComingTodosCounMultiple(){
        val expected = 4
        val repository: TodoRepository = mock()
        whenever(repository.getUpcomingTodosCount())
            .thenReturn(MutableLiveData(expected))

        val model = ListViewModel(repository)

        val count = model.upcomingTodosCount.value
        assertNotNull(count)
        assertEquals(expected, count)
    }



    @Test
    fun testToggleTodo(){
        val repository: TodoRepository = mock()
        val id = "fake id"
        val model = ListViewModel(repository)

        model.toggleTodo(id)

        // no need for thenReturn since toggleToDo ret nothing
        // this just verifies that there's a repo and it's toggleTodo() was called and id was passed
        verify(repository)
            .toggleTodo(id)
    }


    @Test
    fun testToggleTodoNotFound(){
        val repository: TodoRepository = mock()
        val exceptionMessage = "Todo not found"
        val id = "fake id"

        whenever(repository.toggleTodo(id))
            .thenThrow(IllegalArgumentException(exceptionMessage))
        val model = ListViewModel(repository)

        expectedException.expect(IllegalArgumentException::class.java)
        expectedException.expectMessage(exceptionMessage)    // verifies that error message gotten back is correct

        model.toggleTodo(id)

        // no need for thenReturn since toggleToDo ret nothing
        // this just verifies that there's a repo and it's toggleTodo() ws called and right id passsed
        verify(repository)
            .toggleTodo(id)
    }

}