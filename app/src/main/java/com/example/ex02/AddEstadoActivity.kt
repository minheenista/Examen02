package com.example.ex02

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.room.Room
import com.example.ex02.room.Estado
import com.example.ex02.room.database
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AddEstadoActivity : AppCompatActivity() {
    lateinit var btnBackEstados : Button
    lateinit var etNombreEstado : EditText
    lateinit var btnGuardarEstado : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_estado)

        btnBackEstados = findViewById(R.id.btnBackEstados)
        btnGuardarEstado = findViewById(R.id.btnGuardarEstado)
        etNombreEstado = findViewById((R.id.etNombreEstado))

        btnBackEstados.setOnClickListener {
            val intentBackEstados = Intent(this, EstadosActivity::class.java)
            startActivity(intentBackEstados)
        }

        btnGuardarEstado.setOnClickListener {
            agregarEstado()

        }
    }

    private fun agregarEstado(){
        val nombreEstado = etNombreEstado.text.toString()

        if(nombreEstado.isNotEmpty()){
            //referencia a la base de datos
            val db = Room.databaseBuilder(
                applicationContext, database::class.java, "database").build()

            val estadoDAO= db.estadoDAO()

            //hillo de entrada y salida
            CoroutineScope(Dispatchers.IO).launch {
                estadoDAO.newEstado(Estado(nombreEstado = nombreEstado))
                val listadoEstados: List<Estado> = estadoDAO.getAllEstados()
                runOnUiThread {
                    etNombreEstado.setText("")
                }
            }
            val intentRegresar = Intent(this, EstadosActivity::class.java)
            startActivity(intentRegresar)
            Toast.makeText(this, "Estado Agregado con exito!", Toast.LENGTH_SHORT).show()

        } else {
            Toast.makeText(this, "Rellena los campos", Toast.LENGTH_SHORT).show()

        }
    }
}