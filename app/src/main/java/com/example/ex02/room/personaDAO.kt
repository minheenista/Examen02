package com.example.ex02.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface personaDAO {
    @Query("SELECT * FROM persona")
    fun getAllPersonas(): List<Persona>

    @Query("SELECT * FROM persona WHERE id=:idPersona")
    fun getPersonaById(idPersona: Int): Persona?

    @Insert
    fun newPersona(persona: Persona)

    @Update
    fun updatePersona(persona: Persona)

    @Delete
    fun deletePersona(persona: Persona)
}