package com.example.roomkotlin.model

import androidx.room.Entity
import androidx.room.PrimaryKey
@Entity
data class Task(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val title: String
)