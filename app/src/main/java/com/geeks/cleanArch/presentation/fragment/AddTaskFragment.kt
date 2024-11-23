package com.geeks.cleanArch.presentation.fragment

import MainActivityViewModel
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.viewModelScope
import androidx.navigation.fragment.findNavController
import com.geeks.cleanArch.R
import com.geeks.cleanArch.TaskUI
import com.geeks.cleanArch.databinding.FragmentAddTaskBinding
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class AddTaskFragment : Fragment() {

    private val binding by lazy {
        FragmentAddTaskBinding.inflate(layoutInflater)
    }
    private val viewModel: MainActivityViewModel by viewModels()
    private lateinit var imageLauncher: ActivityResultLauncher<String>
    private var imageString: String? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        imageLauncher = registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
            uri?.let {
                binding.changePhoto.setImageURI(uri)
                imageString = uri.toString()
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpListeners()
    }

    private fun setUpListeners() {
        binding.changePhoto.setOnClickListener {
            imageLauncher.launch("image/*")
        }

        binding.btnAddTask.setOnClickListener {
            val task = binding.etTaskName.text.toString()
            val date = binding.etTaskDate.text.toString()

            val taskUI = TaskUI(0, task, date, imageString.toString())
            viewModel.insertTask(taskUI)

            viewModel.viewModelScope.launch {
                viewModel.insertMessageFlow.collectLatest {
                    Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
                }
            }
            findNavController().navigate(R.id.action_addTaskFragment_to_taskListFragment)


        }
    }
}