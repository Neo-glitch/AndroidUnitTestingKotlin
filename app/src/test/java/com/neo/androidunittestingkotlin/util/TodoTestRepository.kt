package com.neo.androidunittestingkotlin.util

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.neo.androidunittestingkotlin.data.Todo
import com.neo.androidunittestingkotlin.data.TodoRepository


/**
 * Test repo that mimics the TodoRoomRepository
 */
class TodoTestRepository(private val todos: ArrayList<Todo>): TodoRepository {
    override fun getAllTodos(): LiveData<List<Todo>> {
        // mutable liveData makes our array list to be a LiveData obj
        return MutableLiveData(todos)
    }

    override fun insert(todo: Todo) {
        TODO("Not yet implemented")
    }

    override fun toggleTodo(id: String) {
        TODO("Not yet implemented")
    }

    override fun getUpcomingTodosCount(): LiveData<Int> {
        val count = todos.count{
            // ret a count of items in list that pass this condition
            !it.completed &&
                    it.dueDate != null &&
                    it.dueDate!! >= System.currentTimeMillis()
        }
        return MutableLiveData(count)
    }
}