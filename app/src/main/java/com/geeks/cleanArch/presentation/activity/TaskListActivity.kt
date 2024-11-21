package com.geeks.cleanArch.presentation.activity

import com.geeks.cleanArch.TaskListAdapter
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.geeks.cleanArch.databinding.ActivityTaskListBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class TaskListActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityTaskListBinding.inflate(layoutInflater)
    }
    private val viewModel: MainActivityViewModel by viewModel()
    private val taskAdapter = TaskListAdapter(emptyList())
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        initAdapter()
        initObservers()
        viewModel.loadTasks()
    }

    private fun initAdapter() {
        binding.rView.adapter = taskAdapter
    }

    private fun initObservers() {
        viewModel.tasks.observe(this) { tasks ->
            taskAdapter.updateTasks(tasks)
        }
    }
}
