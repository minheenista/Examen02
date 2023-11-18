package com.example.ex02

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import androidx.room.Room
import com.example.ex02.room.Estado
import com.example.ex02.room.Persona
import com.example.ex02.room.database
import com.example.ex02.room.personaDAO
import com.example.examen02.EstadoSpinnerAdapter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AddPersonaActivity : AppCompatActivity() {
    lateinit var btnBackPersonas : Button
    lateinit var spEstado : Spinner
    lateinit var etNombrePersona : EditText
    lateinit var etEdad : EditText
    lateinit var btnGuardarPersona : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_persona)

        btnBackPersonas = findViewById(R.id.btnBackPersonas)
        spEstado = findViewById(R.id.spEstado)
        etNombrePersona = findViewById(R.id.etNombrePersona)
        etEdad = findViewById(R.id.etEdad)
        btnGuardarPersona = findViewById(R.id.btnGuardarPersona)

        CoroutineScope(Dispatchers.IO).launch {
            val db = Room.databaseBuilder(applicationContext, database::class.java, "database").build()

            val estadoDAO = db.estadoDAO()

            val listadoEstados : List<Estado> = estadoDAO.getAllEstados()

            CoroutineScope(Dispatchers.Main).launch {
                val adapterEstado = EstadoSpinnerAdapter(this@AddPersonaActivity, android.R.layout.simple_spinner_item, listadoEstados)
                spEstado.adapter = adapterEstado
            }
        }

        btnGuardarPersona.setOnClickListener {
            agregarPersona()
        }

        btnBackPersonas.setOnClickListener {
            val intentBackPersonas = Intent(this, PersonasActivity::class.java)
            startActivity(intentBackPersonas)
        }

    }

    private fun agregarPersona(){
        val nombrePersona = etNombrePersona.text.toString()
        val edadPersona = etEdad.text.toString().toInt()
        val selectedEstado = spEstado.selectedItem as Estado
        val idEstado = selectedEstado.id_estado

        if (nombrePersona.isNotEmpty()){
            CoroutineScope(Dispatchers.IO).launch {
                val db = Room.databaseBuilder(
                    applicationContext, database::class.java, "database"
                ).build()

                val personaDAO = db.personaDAO()

                CoroutineScope(Dispatchers.IO).launch {
                    personaDAO.newPersona(Persona(nombrePersona = nombrePersona, edadPersona = edadPersona, estadoPersona = idEstado))
                    runOnUiThread {
                        etNombrePersona.setText("")
                        etEdad.setText("")
                    }
                }

            }
            val intentRegresar = Intent(this, PersonasActivity::class.java)
            startActivity(intentRegresar)
            Toast.makeText(this, "Persona Agregada con exito!", Toast.LENGTH_SHORT).show()

        } else {
            Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show()

        }



    }
}