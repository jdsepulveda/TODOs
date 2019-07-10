package com.todo.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "daily_task_table")
data class DBTask(
    @PrimaryKey(autoGenerate = true)
    var taskId: Long = 0L,

    @ColumnInfo(name = "task_title")
    var title: String = "",

    @ColumnInfo(name = "task_description")
    var desc: String = "",

    @ColumnInfo(name = "task_priority")
    var priorityLevel: Boolean = false,

    @ColumnInfo(name = "task_status")
    var status: Boolean = false
)