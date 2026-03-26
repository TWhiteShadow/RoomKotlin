package com.example.roomkotlin.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.roomkotlin.data.TaskDao
import com.example.roomkotlin.model.Task
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
class TaskViewModel(private val dao: TaskDao) : ViewModel() {
    // On transforme le Flow de Room en StateFlow (état observable par Compose)
    val tasks: StateFlow<List<Task>> = dao.getAll()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )
    fun addTask(title: String) {
        if (title.isNotBlank()) {
            viewModelScope.launch {
                dao.insert(Task(title = title))
            }
        }
    }
    fun deleteTask(task: Task) {
        viewModelScope.launch {
            dao.delete(task)
        }
    }
}