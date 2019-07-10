package com.todo.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.todo.database.DBTask
import com.todo.database.TaskDatabaseDAO
import com.todo.model.Task
import kotlinx.coroutines.*

class TaskViewModel(val database : TaskDatabaseDAO) : ViewModel() {
    private var viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    private val _backToTaskList = MutableLiveData<Boolean?>()
    val backToTaskList : LiveData<Boolean?>
        get() = _backToTaskList

    fun onProcessTask(task: Task) {
        if (task.taskId > 0) {
            onUpdateTask(task)
        } else {
            onSaveTask(task)
        }
    }

    fun onSaveTask(task: Task) {
        uiScope.launch {
            val newTask = DBTask()
            newTask.title = task.title
            newTask.desc = task.desc
            newTask.priorityLevel = task.priorityLevel
            newTask.status = task.status
            insert(newTask)
        }
        _backToTaskList.value = true
    }

    fun onUpdateTask(task: Task) {
        uiScope.launch {
            var dbTask = database.getTaskWithId(task.taskId)
            if (dbTask == null) {
                onSaveTask(task)
            } else {
                dbTask.title = task.title
                dbTask.desc = task.desc
                dbTask.priorityLevel = task.priorityLevel
                dbTask.status = task.status
                update(dbTask)
            }
        }
        _backToTaskList.value = true
    }

    private suspend fun insert(task: DBTask) {
        withContext(Dispatchers.IO) {
            database.insert(task)
        }
    }

    private suspend fun update(task: DBTask) {
        withContext(Dispatchers.IO) {
            database.update(task)
        }
    }

    private suspend fun getLastTask() : DBTask? {
        return withContext(Dispatchers.IO) {
            val task = database.getLastTask()
            task
        }
    }

    fun doneBackToTaskList() {
        _backToTaskList.value = null
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}