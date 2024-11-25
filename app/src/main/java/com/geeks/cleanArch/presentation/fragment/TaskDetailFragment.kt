package com.geeks.cleanArch.presentation.fragment

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
import com.geeks.cleanArch.presentation.fragment.viewmodel.TaskViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import com.example.domain.result.Result
import com.geeks.cleanArch.presentation.model.TaskUI


class TaskDetailFragment : Fragment(R.layout.fragment_task_detail) {

    private val binding by viewBinding(FragmentTaskDetailBinding::bind)
    private val viewModel: TaskViewModel by viewModels()
    private val navArgs by navArgs<TaskDetailFragmentArgs>()
    private var taskUI: TaskUI? = null


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val taskId = navArgs.taskId
        viewModel.getTask(taskId)
        setupListeners()
        updateUI()

        viewModel.viewModelScope.launch(Dispatchers.Main) {
            viewModel.taskStateFlow.collectLatest {
                when (it) {
                    is Result.Success -> {
                        taskUI = it.data
                    }

                    is Result.Failed-> {
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
                viewModel.viewModelScope.launch {
                    viewModel.getTask(it.id)
                }
                findNavController().navigateUp()
            }
        }
    }

    private fun updateUI() {
        binding.etTaskName.setText(taskUI?.taskName)
        binding.etTaskDate.setText(taskUI?.taskDate)
        taskUI?.taskImage?.let {
            binding.addPhoto.setImageURI(Uri.parse(it))
        }
    }
}
