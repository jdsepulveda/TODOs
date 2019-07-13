package com.todo.database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface TaskDatabaseDAO {
    @Insert
    fun insert(dbTask: DBTask)

    @Update
    fun update(dbTask: DBTask)

    @Query("DELETE FROM daily_task_table WHERE taskId = :key")
    fun delete(key: Long)

    @Query("DELETE FROM daily_task_table")
    fun deleteAll()

    @Query("SELECT * FROM daily_task_table WHERE taskId = :key")
    fun getTaskWithId(key: Long): DBTask?

    @Query("SELECT * FROM daily_task_table WHERE task_status = 0 ORDER BY task_priority DESC")
    fun getOpenTasks(): LiveData<List<DBTask>>

    @Query("SELECT * FROM daily_task_table WHERE task_status = 1 ORDER BY task_priority DESC")
    fun getClosedTasks(): LiveData<List<DBTask>>

    @Query("SELECT * FROM daily_task_table ORDER BY taskId DESC LIMIT 1")
    fun getLastTask(): DBTask?
}