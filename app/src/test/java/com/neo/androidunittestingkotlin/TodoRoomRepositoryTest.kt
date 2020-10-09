package com.neo.androidunittestingkotlin

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.jraska.livedata.test
import com.neo.androidunittestingkotlin.data.Todo
import com.neo.androidunittestingkotlin.data.TodoDao
import com.neo.androidunittestingkotlin.data.TodoRoomDatabase
import com.neo.androidunittestingkotlin.data.TodoRoomRepository
import com.nhaarman.mockitokotlin2.*
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.ExpectedException
import org.junit.runner.RunWith
import java.lang.RuntimeException


@RunWith(AndroidJUnit4::class)
class TodoRoomRepositoryTest {

    // rule tells testRunner to run AsyncTask immediately rather than waiting
    @Rule
    @JvmField
    val testRule = InstantTaskExecutorRule()

    // exceptionRule
    @Rule
    @JvmField
    val exceptionRule = ExpectedException.none()

    val now = System.currentTimeMillis()
    val day = 1000 * 60 * 60 * 24


    // database object
    private lateinit var db: TodoRoomDatabase

    @Before
    fun setUp(){
        // setup method just creates the db

        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(context, TodoRoomDatabase::class.java)
            .allowMainThreadQueries()   // allows creation of db on mainThread
            .build()
    }

    @After
    fun tearDown(){
        db.close()
    }


    @Test
    fun testGetUpComingTodoCountEmpty(){
        // arrange block
        val dao = spy(db.todoDao())     // spies the todoDao obj to run db tasks
        val repo = TodoRoomRepository(dao)
        val expected = 0

        // act
        val actual = repo.getUpcomingTodosCount().test().value()   // gets the real value of the liveData ret
        assertEquals(expected, actual)

        verify(dao).getDateCount(any())
    }

    @Test
    fun testGetUpComingTodoCountSingleMatch(){
        val dao = spy(db.todoDao())     // spies the todoDao obj to run db tasks

        db.todoDao().insert(Todo("5", "Todo 5", now - day, false, now))
        db.todoDao().insert(Todo("4", "Todo 4", now + day, true, now))
        db.todoDao().insert(Todo("3", "Todo 3", now + day, false, now))


        val repo = TodoRoomRepository(dao)
        val expected = 1

        // act
        val actual = repo.getUpcomingTodosCount().test().value()   // gets the real value of the liveData ret
        assertEquals(expected, actual)

        verify(dao).getDateCount(any())
    }

    @Test
    fun testAllTodosMultiple(){
        val dao = spy(db.todoDao())     // spies the todoDao obj to run db tasks

        val testTodo = Todo("5", "Todo 5", now - day, false, now)
        db.todoDao().insert(testTodo)
        db.todoDao().insert(Todo("4", "Todo 4", now + day, true, now))
        db.todoDao().insert(Todo("3", "Todo 3", now + day, false, now))


        val repo = TodoRoomRepository(dao)
        val expected = 3

        // act
        val actual = repo.getAllTodos().test().value()   // gets the real value of the liveData ret
        assertEquals(expected, actual.size)

        verify(dao).getAllTodos()
        assertTrue(actual.contains(testTodo))   // checks to see if testDodo val exists on the list of todos
    }
    
    
    /////////// Test for write operations /////////
    @Test
    fun testToggleTodoGoodId(){
        // mock dao since we don't need full room infrastructure
        val dao = mock<TodoDao>{
            on(it.toggleTodo(any()))   // method to ret doAnswer if it's called
                .doAnswer {  // mock answer to be ret
                    val id = it.arguments[0]
                    require(id != "bad") {"bad id"}
                    1
                }
        }

        val repo = TodoRoomRepository(dao)
        val id = "good"

        repo.toggleTodo(id)

        // assert that dao toggleTodo() is called with that param id
        verify(dao).toggleTodo(id)
    }


    fun testToggleTodoBadId(){
        // mock dao since we don't need full room infrastructure
        val dao = mock<TodoDao>{
            on(it.toggleTodo(any()))   // method to ret doAnswer if it's called
                .doAnswer {  // mock answer to be ret
                    val id = it.arguments[0]
                    require(id != "bad") {"bad id"}
                    1
                }
        }

        val repo = TodoRoomRepository(dao)
        val id = "bad"
        exceptionRule.expect(RuntimeException::class.java)

        repo.toggleTodo(id)

        // assert that dao toggleTodo() is called with that param id
        verify(dao).toggleTodo(id)
    }

    @Test
    fun testInsert(){
        val dao = mock<TodoDao>()
        val repo = TodoRoomRepository(dao)
        val expected = Todo("1", "Test todo", null, false, now)

        //act
        repo.insert(expected)

        // checks Todos obj arg submitted to the dao
        argumentCaptor<Todo>().apply {
            verify(dao).insert(capture())
            // checks that expected value is same as first value passed to dao insert() fun
            assertEquals(expected, firstValue)
        }
    }

}