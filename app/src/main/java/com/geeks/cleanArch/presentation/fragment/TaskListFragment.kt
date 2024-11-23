package com.geeks.cleanArch.presentation.fragment

import MainActivityViewModel
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.viewModelScope
import androidx.navigation.fragment.findNavController
import com.geeks.cleanArch.R
import com.geeks.cleanArch.databinding.FragmentTaskListBinding
import com.geeks.cleanArch.presentation.fragment.adapter.TaskListAdapter
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class TaskListFragment : Fragment() {

    private val binding by lazy {
        FragmentTaskListBinding.inflate(layoutInflater)
    }
    private val viewModel: MainActivityViewModel by viewModels()
    private lateinit var taskAdapter: TaskListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        addTask()
        initialize()
        showTask()
        viewModel.loadTasks()
    }

    private fun addTask() {
        binding.btnAddTask.setOnClickListener {
            findNavController().navigate(R.id.action_taskListFragment_to_addTaskFragment)
        }
    }

    private fun initialize() {
        taskAdapter = TaskListAdapter(emptyList(), { task ->
            val action = TaskListFragmentDirections.actionTaskListFragmentToTaskDetailFragment(task)
            findNavController().navigate(action)
        }, { task ->
            viewModel.deleteTask(task)
        })
        binding.rvTask.adapter = taskAdapter
        taskAdapter.attachSwipeToRecyclerView(binding.rvTask)
    }

    private fun showTask() {
        viewModel.viewModelScope.launch {
            viewModel.taskFlow.collectLatest {
                taskAdapter.updateTasks(it)
            }
        }


    }

}