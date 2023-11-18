package com.example.ex02.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface estadoDAO {
    @Query("SELECT * FROM Estado")
    fun getAllEstados(): List<Estado>

    @Query("SELECT * FROM estado WHERE id_estado=:id_estado")
    fun getEstadoById(id_estado: Int): Estado

    @Insert
    fun newEstado(estado: Estado)

    @Update
    fun updateEstado(estado: Estado)

    @Delete
    fun deleteEstado(estado: Estado)
}