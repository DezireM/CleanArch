package com.geeks.cleanArch.presentation.fragment

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope  // Use lifecycleScope for launching coroutines
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.addtask.activity.AddTaskActivity
import com.geeks.cleanArch.R
import com.geeks.cleanArch.presentation.model.TaskUI
import com.geeks.cleanArch.databinding.FragmentTaskListBinding
import com.geeks.cleanArch.presentation.fragment.adapter.TaskListAdapter
import com.geeks.cleanArch.presentation.fragment.viewmodel.LoadingState
import com.geeks.cleanArch.presentation.fragment.viewmodel.TaskViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class TaskListFragment : Fragment(R.layout.fragment_task_list) {

    private val binding by viewBinding(FragmentTaskListBinding::bind)
    private val viewModel: TaskViewModel by viewModels()
    private val taskAdapter = TaskListAdapter(emptyList(), ::onItemClick, ::onTaskDelete)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.loadTasks()
        addTask()
        initialize()
        showTask()
        observeLoadingState()
    }

    private fun addTask() {
        binding.btnAddTask.setOnClickListener {
            val intent = Intent(requireContext(), AddTaskActivity::class.java)
            startActivity(intent)
        }
    }

    private fun initialize() {
        binding.rvTask.adapter = taskAdapter
        taskAdapter.attachSwipeToRecyclerView(binding.rvTask)
    }

    private fun showTask() {
       lifecycleScope.launch {
            viewModel.tasksFlow.collectLatest { tasks ->
                taskAdapter.updateTasks(tasks)
            }
        }
    }

    private fun observeLoadingState() {
        lifecycleScope.launch {
            viewModel.loadingFlow.collect { state ->
                when (state) {
                    is LoadingState.Loading -> {
                    }
                    is LoadingState.Error -> {
                        Toast.makeText(requireContext(), "Error loading tasks", Toast.LENGTH_SHORT).show()
                    }
                    else -> {}
                }
            }
        }
    }

    private fun onItemClick(id: Int) {
        val action = TaskListFragmentDirections.actionTaskListFragmentToTaskDetailFragment(id)
        findNavController().navigate(action)
    }

    private fun onTaskDelete(taskUI: TaskUI) {
        viewModel.deleteTask(taskUI)
    }
}
