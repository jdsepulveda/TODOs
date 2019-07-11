package com.todo.viewModel

import android.util.Log
import androidx.lifecycle.*
import com.todo.database.DBTask
import com.todo.database.TaskDatabaseDAO
import com.todo.model.Task
import kotlinx.coroutines.*
import timber.log.Timber

class TaskViewModel(
    private val taskKey: Long = 0L,
    val database : TaskDatabaseDAO) : ViewModel() {

    private var viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    private val _backToTaskList = MutableLiveData<Boolean?>()
    val backToTaskList : LiveData<Boolean?>
        get() = _backToTaskList

    private var _task = MediatorLiveData<Task>()
    var task : LiveData<Task>? = null
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
            onSaveTask()
        }
    }

    fun onSaveTask() {
        uiScope.launch {
            val newTask = DBTask()
            newTask.title = newTask.title
            newTask.desc = newTask.desc
            newTask.priorityLevel = newTask.priorityLevel
            newTask.status = newTask.status
            insert(newTask)
        }
        _backToTaskList.value = true
    }

    fun onUpdateTask(task: Task) {
        uiScope.launch {
            withContext(Dispatchers.IO) {
                val dbTask = database.getTaskWithId(task.taskId)
                if (dbTask == null) {
                    onSaveTask()
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