package com.example.roomkotlin

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.room.Room
import com.example.roomkotlin.data.AppDb
import com.example.roomkotlin.ui.TaskScreen
import com.example.roomkotlin.ui.TaskViewModel
import com.example.roomkotlin.ui.TaskViewModelFactory
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val db = Room.databaseBuilder(applicationContext, AppDb::class.java,
            "db").build()
        val dao = db.dao()
        // Récupération du ViewModel via la Factory
        val viewModel: TaskViewModel by viewModels { TaskViewModelFactory(dao) }
        setContent {
            TaskScreen(viewModel)
        }
    }
}