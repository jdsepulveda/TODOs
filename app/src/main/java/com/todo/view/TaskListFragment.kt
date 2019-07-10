package com.todo.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DividerItemDecoration

import com.todo.R
import com.todo.adapter.TaskListAdapter
import com.todo.database.TaskDatabase
import com.todo.databinding.FragmentTaskListBinding
import com.todo.viewModel.TaskListViewModel
import com.todo.viewModel.TaskListViewModelFactory

class TaskListFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding: FragmentTaskListBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_task_list, container, false)

        binding.fBtnAddTask.setOnClickListener { v: View ->
            v.findNavController().navigate(TaskListFragmentDirections.actionTaskListFragmentToTaskFragment())
        }

        (activity as AppCompatActivity).supportActionBar?.title = getString(R.string.todo_list)

        val application = requireNotNull(this.activity).application
        val dataSource = TaskDatabase.getInstance(application).taskDatabaseDAO
        val viewModelFactory = TaskListViewModelFactory(dataSource)
        val taskListViewModel = ViewModelProviders.of(this, viewModelFactory).get(TaskListViewModel::class.java)

        val adapter = TaskListAdapter()
        binding.rvTaskList.adapter = adapter
        binding.rvTaskList.addItemDecoration(DividerItemDecoration(activity, DividerItemDecoration.VERTICAL))

        taskListViewModel.tasks.observe(this, Observer {
            it?.let {
                adapter.submitList(it)
            }
        })

        return binding.root
    }
}