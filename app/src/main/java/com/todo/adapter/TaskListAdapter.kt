package com.todo.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.todo.database.DBTask
import com.todo.databinding.ItemTaskListBinding

class TaskListAdapter(val clickListener: TaskListListener) :
    ListAdapter<DBTask, TaskListAdapter.ViewHolder>(DBTaskDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskListAdapter.ViewHolder {
        return ViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item, clickListener)
    }

    class ViewHolder private constructor(val binding: ItemTaskListBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(item: DBTask, clickListener: TaskListListener) {
            binding.clickListener = clickListener
            binding.dbTask = item
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ItemTaskListBinding.inflate(layoutInflater, parent, false)

                return ViewHolder(binding)
            }
        }
    }
}

class TaskListListener(val clickListener: (taskId: Long) -> Unit) {
    fun onClick(task: DBTask) = clickListener(task.taskId)
}

class DBTaskDiffCallback : DiffUtil.ItemCallback<DBTask>() {
    override fun areItemsTheSame(oldItem: DBTask, newItem: DBTask): Boolean {
        return oldItem.taskId == newItem.taskId
    }

    override fun areContentsTheSame(oldItem: DBTask, newItem: DBTask): Boolean {
        return oldItem == newItem
    }
}