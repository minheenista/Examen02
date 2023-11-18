package com.example.ex02.room

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Persona::class, Estado::class], version = 3)
abstract class database : RoomDatabase() {
    abstract fun estadoDAO() : estadoDAO
    abstract fun personaDAO(): personaDAO
}