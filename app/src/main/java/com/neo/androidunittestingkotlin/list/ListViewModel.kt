package com.neo.androidunittestingkotlin.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.neo.androidunittestingkotlin.data.Todo
import com.neo.androidunittestingkotlin.data.TodoRepository


/**
 * View model that receives data from the repo and pass up to activity
 */
class ListViewModel(
    private val todoRepository: TodoRepository
) : ViewModel() {

    val allTodos: LiveData<List<Todo>> = todoRepository.getAllTodos()
    val upcomingTodosCount: LiveData<Int> = todoRepository.getUpcomingTodosCount()

    fun toggleTodo(id: String) {
        todoRepository.toggleTodo(id)
    }

}