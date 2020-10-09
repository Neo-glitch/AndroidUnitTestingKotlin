package com.neo.androidunittestingkotlin.data

import androidx.lifecycle.LiveData
import com.neo.androidunittestingkotlin.data.Todo


// this layer of abstraction is interface that rest of app from here up interact with
// The layer also makes it possible to swap the dataSource i.e such as Firebase or any other remote db
interface TodoRepository {

    fun getAllTodos(): LiveData<List<Todo>>

    fun insert(todo: Todo)

    fun toggleTodo(id: String)

    fun getUpcomingTodosCount(): LiveData<Int>
}