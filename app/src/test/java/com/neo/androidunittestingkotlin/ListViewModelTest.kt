package com.neo.androidunittestingkotlin

import com.neo.androidunittestingkotlin.data.Todo
import com.neo.androidunittestingkotlin.data.TodoRepository
import com.neo.androidunittestingkotlin.list.ListViewModel
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import com.neo.androidunittestingkotlin.util.TodoTestRepository
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.ExpectedException

class ListViewModelTest {


    @Rule
    @JvmField
    val expectedException = ExpectedException.none()    // rule tells junit not to expect exception by default

    // repo interface implemented in TodoTestRepo var for our test
    lateinit var repository: TodoRepository

    @Before
    fun setUp(){
        val now = System.currentTimeMillis()
        val day = 1000 * 60 * 60 * 24

        val todos = ArrayList<Todo>()
        var todo = Todo("1", "Todo 1", null, false, now)
        todos.add(todo)
        todo = Todo("2", "Todo 2", now + day, false, now)
        todos.add(todo)
        todo = Todo("3", "Todo 3", now + day, false, now)
        todos.add(todo)
        todo = Todo("4", "Todo 4", now + day, false, now)
        todos.add(todo)
        todo = Todo("5", "Todo 5", now + day, false, now)
        todos.add(todo)

        repository = TodoTestRepository(todos)

    }

    @Test
    fun testAllTodos(){
        val expected = 5

        // init the ListViewModel with repo as that used in test
        val model = ListViewModel(repository)

        // act, gets list of todos from repo
        val todos = model.allTodos.value

        assertNotNull(todos)
        assertEquals(expected, todos!!.size)
    }

    @Test
    fun testUpComingTodo(){
        val expected = 4
        val model = ListViewModel(repository)

        val count = model.upcomingTodosCount.value
        assertNotNull(count)
        assertEquals(expected, count)
    }

    @Test
    fun testToggleTodo(){
        val id = "fake id"
        val model = ListViewModel(repository)
        expectedException.expect(NotImplementedError::class.java)
        model.toggleTodo(id)
    }

}