package com.geeks.cleanArch.presentation.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.geeks.cleanArch.TaskUI
import com.geeks.cleanArch.databinding.ActivityMainBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    private val viewModel: MainActivityViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setupListeners()
    }

    private fun setupListeners() {
        binding.addTaskButton.setOnClickListener {
            val intent = Intent(this, TaskListActivity::class.java)
            startActivity(intent)
        }

        binding.addTaskButton.setOnClickListener {
            val task = binding.etTask.text.toString()
            val date = binding.etDate.text.toString()

            if (task.isBlank() || date.isBlank()) {
                Toast.makeText(this, "Fields are empty", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            viewModel.insertTask(taskUI = TaskUI(0, task, date))
        }

        viewModel.insertMessage.observe(this) {
            Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
        }
    }
}