package com.example.roomkotlin.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
@Composable
fun TaskScreen(viewModel: TaskViewModel) {
    // Observation de l'état (List) depuis le ViewModel
    val tasks by viewModel.tasks.collectAsState()
    // État local uniquement pour le texte en cours de saisie
    var text by remember { mutableStateOf("") }
    Column(Modifier.padding(16.dp)) {
        Row {
            TextField(
                value = text,
                onValueChange = { text = it },
                modifier = Modifier.weight(1f),
                placeholder = { Text("Faire...") }
            )
            Button(onClick = {
                viewModel.addTask(text) // Délégation au ViewModel
                text = ""
            }) {
                Text("OK")
            }
        }
        LazyColumn {
            items(tasks) { task ->
                TextButton(onClick = { viewModel.deleteTask(task) }) {
                    Text("${task.title} (Supprimer)")
                }
            }
        }
    }
}