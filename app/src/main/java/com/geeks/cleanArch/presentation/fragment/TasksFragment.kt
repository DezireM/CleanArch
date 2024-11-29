package com.geeks.cleanArch.presentation.fragment

import TasksViewModel
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.viewModelScope
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.geeks.cleanArch.R
import com.geeks.cleanArch.databinding.FragmentTasksBinding
import com.geeks.cleanArch.presentation.fragment.adapter.TasksAdapter
import com.geeks.cleanArch.presentation.model.TaskUI
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import com.example.domain.result.Result


class TasksFragment : Fragment(R.layout.fragment_tasks) {

    private val binding by viewBinding(FragmentTasksBinding::bind)
    private val viewModel: TasksViewModel by viewModels()
    private val taskAdapter = TasksAdapter(emptyList(), ::onTaskClick, ::onTaskDelete)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.loadTasks()

        setupAddTaskButton()
        initializeRecyclerView()

        observeTasks()
    }

    private fun setupAddTaskButton() {
        binding.btnAddTask.setOnClickListener {
            val action = TasksFragmentDirections.actionTasksFragmentToAddTaskFragment()
            findNavController().navigate(action)
        }
    }

    private fun initializeRecyclerView() {
        binding.rvTasks.adapter = taskAdapter
        taskAdapter.attachSwipeToRecyclerView(binding.rvTasks)
    }

    private fun observeTasks() {
        viewModel.viewModelScope.launch {
            viewModel.tasksFlow.collectLatest { result ->
                when (result) {
                    is Result.Success -> {
                        taskAdapter.updateTasks(result.data)
                        binding.progressBar.visibility = View.GONE
                    }
                    is Result.Failed -> {
                        Toast.makeText(requireContext(), result.message, Toast.LENGTH_SHORT).show()
                        binding.progressBar.visibility = View.GONE
                    }
                    Result.Loading -> {
                        binding.progressBar.visibility = View.VISIBLE
                    }
                }
            }
        }
    }


    private fun onTaskClick(taskId: Int) {
        val action = TasksFragmentDirections.actionTasksFragmentToTaskDetailFragment(taskId)
        findNavController().navigate(action)
    }

    private suspend fun onTaskDelete(taskUI: TaskUI) {
        viewModel.deleteTask(taskUI)
    }
}
