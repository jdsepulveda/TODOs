package com.todo.viewModel

import androidx.lifecycle.*
import com.todo.database.DBTask
import com.todo.database.TaskDatabaseDAO
import com.todo.model.Task
import kotlinx.coroutines.*

class TaskViewModel(
    private val taskKey: Long = 0L,
    val database : TaskDatabaseDAO) : ViewModel() {

    private var viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    private val _backToTaskList = MutableLiveData<Boolean?>()
    val backToTaskList : LiveData<Boolean?>
        get() = _backToTaskList

    private var _task = MediatorLiveData<Task>()
    val task : LiveData<Task>
        get() = _task

    init {
        uiScope.launch {
            withContext(Dispatchers.IO) {
                _task.postValue(DBTask.asDomainModel(database.getTaskWithId(taskKey)))
            }
        }
    }

    fun onProcessTask(task: Task) {
        if (task.taskId > 0) {
            onUpdateTask(task)
        } else {
            onSaveTask(task)
        }
    }

    private fun onSaveTask(task: Task) {
        uiScope.launch {
            withContext(Dispatchers.IO) {
                val newTask = DBTask()
                newTask.title = task.title
                newTask.desc = task.desc
                newTask.priorityLevel = task.priorityLevel
                newTask.status = task.status
                insert(newTask)
            }
        }
        _backToTaskList.value = true
    }

    private fun onUpdateTask(task: Task) {
        uiScope.launch {
            withContext(Dispatchers.IO) {
                val dbTask = database.getTaskWithId(task.taskId)
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

    fun onDelete(task: Task) {
        uiScope.launch {
            withContext(Dispatchers.IO) {
                database.delete(task.taskId)
            }
        }
        _backToTaskList.value = true
    }

    fun doneBackToTaskList() {
        _backToTaskList.value = null
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}