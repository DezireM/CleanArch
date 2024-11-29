package com.geeks.cleanArch.presentation.fragment

import TasksViewModel
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.viewModelScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import by.kirich1409.viewbindingdelegate.viewBinding
import com.geeks.cleanArch.R
import com.geeks.cleanArch.databinding.FragmentTaskDetailBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import com.geeks.cleanArch.presentation.model.TaskUI
import com.example.domain.result.Result


class TaskDetailFragment : Fragment(R.layout.fragment_task_detail) {

    private val binding by viewBinding(FragmentTaskDetailBinding::bind)
    private val viewModel: TasksViewModel by viewModels()
    private val navArgs by navArgs<TaskDetailFragmentArgs>()
    private var taskUI: TaskUI? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getTask(navArgs.taskId)
        setupListeners()
        updateUI()


        viewModel.viewModelScope.launch(Dispatchers.IO) {
            viewModel.tasksFlow.collect { task ->
                task.let {
                    taskUI = task[id]
                    updateUI()
                }
            }
        }

        viewModel.viewModelScope.launch(Dispatchers.Main) {
            viewModel.taskStateFlow.collectLatest {
                when (it) {
                    is Result.Success -> {
                        taskUI = it.data
                    }

                    is Result.Failed -> {
                        Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                    }

                    is Result.Loading -> {
                        Toast.makeText(requireContext(), "Loading", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

    private fun setupListeners() {
        binding.btnSaveTask.setOnClickListener {
            val updatedTask = taskUI?.copy(
                taskName = binding.etTaskName.text.toString(),
                taskDate = binding.etTaskDate.text.toString()
            )
            updatedTask?.let {
                viewModel.updateTask(it)
                findNavController().navigateUp()
            }
        }
    }
    private fun updateUI() {
        taskUI?.let {
            binding.etTaskName.setText(it.taskName)
            binding.etTaskDate.setText(it.taskDate)
            it.taskImage?.let { image ->
                binding.addPhoto.setImageURI(Uri.parse(image))
            }
        }
    }
}
