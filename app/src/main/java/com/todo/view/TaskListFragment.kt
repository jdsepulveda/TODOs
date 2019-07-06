package com.todo.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController

import com.todo.R
import com.todo.databinding.FragmentTaskListBinding

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

        return binding.root
    }
}