package com.neo.androidunittestingkotlin

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.neo.androidunittestingkotlin.add.AddViewModel
import com.neo.androidunittestingkotlin.data.TodoRepository
import com.neo.androidunittestingkotlin.list.ListViewModel

@Suppress("UNCHECKED_CAST")
class ViewModelFactory constructor(private val todoRepository: TodoRepository) :
    ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>) =
        with(modelClass) {
            when {
                isAssignableFrom(ListViewModel::class.java) ->
                    ListViewModel(todoRepository)
                isAssignableFrom(AddViewModel::class.java) ->
                    AddViewModel(todoRepository)
                else ->
                    throw IllegalArgumentException("ViewModel class (${modelClass.name}) is not mapped")
            }
        } as T
}

