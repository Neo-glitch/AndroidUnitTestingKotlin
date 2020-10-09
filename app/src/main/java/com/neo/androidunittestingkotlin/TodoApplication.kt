package com.neo.androidunittestingkotlin

import android.app.Application
import com.neo.androidunittestingkotlin.data.TodoRepository
import com.neo.androidunittestingkotlin.data.TodoRoomDatabase
import com.neo.androidunittestingkotlin.data.TodoRoomRepository

class TodoApplication : Application() {

    val todoRepository: TodoRepository
        get() = TodoRoomRepository(TodoRoomDatabase.getInstance(this.applicationContext)!!.todoDao())
}