package com.todo.database

import androidx.room.*

@Dao
interface TaskDatabaseDAO {
    @Insert
    fun insert(task: Task)

    @Update
    fun update(task: Task)

    @Query("DELETE FROM daily_task_table WHERE taskId = :key")
    fun delete(key: Long)

    @Query("DELETE FROM daily_task_table")
    fun deleteAll()

    @Query("SELECT * FROM daily_task_table WHERE taskId = :key")
    fun getTaskWithId(key: Long): Task?

    @Query("SELECT * FROM daily_task_table WHERE task_status = 1 ORDER BY task_priority DESC")
    fun getOpenTasks(): Task?

    @Query("SELECT * FROM daily_task_table WHERE task_status = 0 ORDER BY task_priority DESC")
    fun getClosedTasks(): Task?
}