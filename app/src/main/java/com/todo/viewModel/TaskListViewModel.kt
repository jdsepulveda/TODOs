package com.todo.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.todo.database.TaskDatabaseDAO

class TaskListViewModel(val database: TaskDatabaseDAO) : ViewModel() {
    val tasks = database.getOpenTasks()

    private val _navigateToTaskDetail = MutableLiveData<Long>()
    val navigateToTaskDetail
        get() = _navigateToTaskDetail

    fun onTaskClicked(id: Long) {
        _navigateToTaskDetail.value = id
    }

    fun onTaskDetailNavigated() {
        _navigateToTaskDetail.value = null
    }
}