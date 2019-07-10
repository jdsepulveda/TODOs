package com.todo.viewModel

import androidx.lifecycle.ViewModel
import com.todo.database.TaskDatabaseDAO

class TaskListViewModel(val database: TaskDatabaseDAO) : ViewModel() {
    val tasks = database.getOpenTasks()
}