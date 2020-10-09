package com.neo.androidunittestingkotlin.data

import androidx.room.Entity
import androidx.room.PrimaryKey



// class used to create a table with same class name and cols as the fields
@Entity
data class Todo(

    @PrimaryKey var id: String,
    var title: String,
    var dueDate: Long?,
    var completed: Boolean,
    var created: Long
)