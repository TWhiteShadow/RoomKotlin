package com.example.roomkotlin.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.roomkotlin.model.Task
@Database(entities = [Task::class], version = 1)
abstract class AppDb : RoomDatabase() {
    abstract fun dao(): TaskDao
}