package com.example.ex02

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import androidx.room.Room
import com.example.ex02.room.Estado
import com.example.ex02.room.Persona
import com.example.ex02.room.database
import com.example.examen02.EstadoSpinnerAdapter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class UpdatePersonaActivity : AppCompatActivity() {
    lateinit var btnBack : Button
    lateinit var etNombrePersona : EditText
    lateinit var etEdadPersona : EditText
    lateinit var spEstados : Spinner
    lateinit var btnUpdate : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update_persona)

        btnBack = findViewById(R.id.btnBackPersonas)
        etNombrePersona = findViewById(R.id.etNombrePersona)
        etEdadPersona = findViewById(R.id.etEdad)
        spEstados = findViewById(R.id.selectEstado)
        btnUpdate = findViewById(R.id.btnUpdate)

        btnBack.setOnClickListener {
            val intentBack = Intent(this, PersonasActivity::class.java)
            startActivity(intentBack)
        }

        val id = intent.getIntExtra("id", 0)
        val nombrePersona = intent.getStringExtra("nombrePersona")
        val edadPersona = intent.getIntExtra("edadPersona", 0)
        val estadoPersona = intent.getIntExtra("estadoPersona", 0)
        val persona = id?.let { Persona(it.toInt(), nombrePersona, edadPersona, estadoPersona) }

        CoroutineScope(Dispatchers.IO).launch {
            val db = Room.databaseBuilder(
                applicationContext, database::class.java, "database"
            ).build()

            val estadoDAO = db.estadoDAO()
            val listadoEstado: List<Estado> = estadoDAO.getAllEstados()
            val idEstados = listadoEstado.map { it.id_estado }

            CoroutineScope(Dispatchers.Main).launch {
                val adaptadorEstados = EstadoSpinnerAdapter(
                    this@UpdatePersonaActivity, android.R.layout.simple_spinner_item, listadoEstado
                )
                spEstados.adapter = adaptadorEstados
                val position = idEstados.indexOf(estadoPersona)
                spEstados.setSelection(position)
            }
        }

        etNombrePersona.setText(nombrePersona.toString())
        etEdadPersona.setText(edadPersona.toString())

        btnUpdate.setOnClickListener {
            if(persona!= null) {
                updatePersona(id)
                Toast.makeText(this, "Persona Actualizado con exito!", Toast.LENGTH_SHORT).show()

                val intentBack = Intent(this, PersonasActivity::class.java)
                startActivity(intentBack)
            } else {
                Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show()

            }
        }
    }

    private fun updatePersona(id:Int){
        val nombrePersona = etNombrePersona.text.toString()
        val edadPersona = etEdadPersona.text.toString().toInt()
        val estadoPersona = spEstados.selectedItem as Estado
        val idEstado = estadoPersona.id_estado
        val updatedPersona = Persona(id, nombrePersona, edadPersona, idEstado)

        val db = Room.databaseBuilder(
            applicationContext, database::class.java, "database"
        ).build()
        val personaDAO = db.personaDAO()
        CoroutineScope(Dispatchers.IO).launch {
            personaDAO.updatePersona(updatedPersona)
        }
    }
}