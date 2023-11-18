package com.example.ex02.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Estado(
    @PrimaryKey(autoGenerate = true) val id_estado: Int=0,
    @ColumnInfo(name = "nombre_estado") val nombreEstado: String?,
)