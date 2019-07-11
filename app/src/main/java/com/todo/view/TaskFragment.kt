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
import androidx.navigation.fragment.findNavController

import com.todo.R
import com.todo.database.TaskDatabase
import com.todo.databinding.FragmentTaskBinding

import com.todo.viewModel.TaskViewModel
import com.todo.viewModel.TaskViewModelFactory

class TaskFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding: FragmentTaskBinding =
                    DataBindingUtil.inflate(inflater, R.layout.fragment_task, container, false)

        (activity as AppCompatActivity).supportActionBar?.title = getString(R.string.task)

        val arguments = TaskFragmentArgs.fromBundle(arguments!!)

        val application = requireNotNull(this.activity).application
        val dataSource = TaskDatabase.getInstance(application).taskDatabaseDAO
        val viewModelFactory = TaskViewModelFactory(arguments.taskId, dataSource)
        val taskViewModel = ViewModelProviders.of(this, viewModelFactory).get(TaskViewModel::class.java)

        binding.lifecycleOwner = this
        binding.taskViewModel = taskViewModel

        taskViewModel.backToTaskList.observe(this, Observer {
            if (it == true) {
                this.findNavController().navigate(
                    TaskFragmentDirections.actionTaskFragmentToTaskListFragment())
                taskViewModel.doneBackToTaskList()
            }
        })

        binding.btnCancel.setOnClickListener { v: View ->
            v.findNavController().navigate(TaskFragmentDirections.actionTaskFragmentToTaskListFragment())
        }

        return binding.root
    }
}