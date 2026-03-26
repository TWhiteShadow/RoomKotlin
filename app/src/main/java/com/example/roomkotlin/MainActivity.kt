package com.example.roomkotlin

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.room.Room
import kotlinx.coroutines.launch
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        /**
         * INITIALISATION DE LA BASE DE DONNÉES
         * databaseBuilder : Crée l'instance de la base "db" sur le stockage du
        téléphone.
         * .build() : Finalise la création.
         */
        val db = Room.databaseBuilder(applicationContext, AppDb::class.java,
            "db").build()
        val dao = db.dao() // On récupère l'accès aux commandes SQL (DAO)
        setContent {
            /**
             * OBSERVATION DES DONNÉES (Le "Tuyau")
             * collectAsState : Transforme le Flow du DAO en un état lisible par
            Compose.
             * Dès que Room change, la variable 'tasks' est mise à jour et l'écran
            se rafraîchit.
             */
            val tasks by dao.getAll().collectAsState(initial = emptyList())
            /**
             * GESTION DE L'ASYNCHRONE ET DE L'ÉTAT LOCAL
             * scope : Permet de lancer des actions vers Room (insert/delete) en
            arrière-plan.
             * text : Mémorise ce que l'utilisateur écrit dans le champ de saisie.
             */
            val scope = rememberCoroutineScope()
            var text by remember { mutableStateOf("") }
// MISE EN PAGE (UI)
            Column(Modifier.padding(16.dp)) {
// LIGNE DE SAISIE
                Row {
                    TextField(
                        value = text,
                        onValueChange = { text = it },
                        modifier = Modifier.weight(1f),
                        placeholder = { Text("Faire...") }
                    )
                    Button(onClick = {
// On lance une Coroutine pour insérer sans figer l'écran
                        scope.launch {
                            if (text.isNotBlank()) {
                                dao.insert(Task(title = text))
                                text = "" // On vide le champ après l'ajout
                            }
                        }
                    }) {
                        Text("OK")
                    }
                }
// LISTE DYNAMIQUE
                LazyColumn {
// Pour chaque tâche dans la liste 'tasks'
                    items(tasks) { task ->
// Un bouton qui affiche le titre et supprime au clic
                        TextButton(onClick = {
                            scope.launch { dao.delete(task) }
                        }) {
                            Text("${task.title} (Supprimer)")
                        }
                    }
                }
            }
        }
    }
}