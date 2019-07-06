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
import com.todo.databinding.FragmentTaskBinding

class TaskFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding: FragmentTaskBinding =
                    DataBindingUtil.inflate(inflater, R.layout.fragment_task, container, false)

        binding.btnCancel.setOnClickListener { v: View ->
            v.findNavController().navigate(TaskFragmentDirections.actionTaskFragmentToTaskListFragment())
        }

        binding.btnSave.setOnClickListener { v: View ->
            v.findNavController().navigate(TaskFragmentDirections.actionTaskFragmentToTaskListFragment())
        }

        (activity as AppCompatActivity).supportActionBar?.title = getString(R.string.task)

        return binding.root
    }
}