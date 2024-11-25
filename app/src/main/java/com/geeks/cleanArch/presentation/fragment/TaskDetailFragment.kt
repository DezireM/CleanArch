
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
import com.geeks.cleanArch.R
import com.geeks.cleanArch.presentation.model.TaskUI
import com.geeks.cleanArch.databinding.FragmentTaskDetailBinding
import com.geeks.cleanArch.presentation.fragment.viewmodel.LoadingState
import com.geeks.cleanArch.presentation.fragment.viewmodel.TaskViewModel
import kotlinx.coroutines.launch

class TaskDetailFragment : Fragment(R.layout.fragment_task_detail) {

    private lateinit var binding: FragmentTaskDetailBinding
    private val viewModel: TaskViewModel by viewModels()
    private val navArgs by navArgs<TaskDetailFragmentArgs>()
    private var taskUI: TaskUI? = null


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpListeners()
        updateUI()

        viewModel.loadTask(navArgs.taskId)

        viewModel.viewModelScope.launch {
            viewModel.taskStateFlow.collect { task ->
                task?.let {
                    taskUI = task
                    updateUI()
                }
            }
        }

        viewModel.viewModelScope.launch {
            viewModel.loadingFlow.collect {state ->
                when(state ) {
                    is LoadingState.Loading -> {}
                    is LoadingState.Error -> {
                        Toast.makeText(requireContext(), "Error updating task", Toast.LENGTH_SHORT).show()
                    }
                    else -> {}
                }
            }
        }
    }

    private fun setUpListeners() {
        binding.btnSaveTask.setOnClickListener {
            val updatedTask = taskUI?.copy(
                taskName = binding.etTaskName.text.toString(),
                taskDate = binding.etTaskDate.text.toString())
            updatedTask?.let {
                viewModel.updateTask(it)
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