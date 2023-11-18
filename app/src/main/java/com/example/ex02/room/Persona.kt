package com.example.ex02.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Persona (
    @PrimaryKey(autoGenerate = true) val id: Int=0,
    @ColumnInfo(name = "nombre") val nombrePersona: String?,
    @ColumnInfo(name = "edad") val edadPersona: Int?,
    @ColumnInfo(name = "estado") val estadoPersona: Int?,
    )