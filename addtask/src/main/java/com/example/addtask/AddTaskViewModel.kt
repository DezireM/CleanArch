package com.example.addtask

import android.os.Build
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.usecase.InsertTaskUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import com.example.domain.result.Result


class AddTaskViewModel(
    private val insertTaskUseCase: InsertTaskUseCase,
) : ViewModel() {

    private val _insertMessageStateFlow = MutableStateFlow("")
    val insertMessageFlow: StateFlow<String> = _insertMessageStateFlow.asStateFlow()

    fun insertTask(taskUI: TaskUI) {
        viewModelScope.launch(Dispatchers.IO) {
            val result = insertTaskUseCase.insertTask(taskUI.toDomain(), Build.VERSION.SDK_INT)

            when (result) {
                is Result.Success -> {
                    _insertMessageStateFlow.value = "Task inserted successfully!"
                }
                is Result.Error -> {
                    _insertMessageStateFlow.value = "Error: ${result.message}"
                }
                is Result.Loading -> {
                    _insertMessageStateFlow.value = "Inserting task..."
                }
            }
        }
    }
}
