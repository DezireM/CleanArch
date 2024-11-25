package com.example.addtask

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.viewModelScope
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.addtask.databinding.FragmentAddTaskBinding
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class AddTaskFragment : Fragment(R.layout.fragment_add_task) {

    private val binding by viewBinding(FragmentAddTaskBinding::bind)
    private val viewModel: AddTaskViewModel by viewModels()
    private var imageString: String? = null
    private val imageLauncher =
        registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
            uri?.let {
                binding.changeImage.setImageURI(uri)
                imageString = uri.toString()
            }
        }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupListeners()

        viewModel.viewModelScope.launch {
            viewModel.insertMessageFlow.collectLatest {
                Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun setupListeners() {

        binding.changeImage.setOnClickListener {
            imageLauncher.launch("image/*")
        }

        binding.btnAddTask.setOnClickListener {
            val task = binding.etTaskName.text.toString()
            val date = binding.etTaskDate.text.toString()

            val taskUI = TaskUI(0, task, date, imageString.toString())
            viewModel.insertTask(taskUI)

            requireActivity().finish()
        }
    }
}