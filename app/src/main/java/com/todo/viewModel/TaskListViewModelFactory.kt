package com.todo.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.todo.database.TaskDatabaseDAO
import java.lang.IllegalArgumentException

class TaskListViewModelFactory(private val dataSource: TaskDatabaseDAO) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TaskListViewModel::class.java)) {
            return TaskListViewModel(dataSource) as T
        }
        throw IllegalArgumentException("Unknown ViewModel Class")
    }
}