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

class UpdateEstadoActivity : AppCompatActivity() {
    lateinit var btnBack:Button
    lateinit var btnUpdateEstado : Button
    lateinit var etNombreEstadoUp : EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update_estado)

        btnBack = findViewById(R.id.btnBackEstados)
        btnUpdateEstado = findViewById(R.id.updateEstado)
        etNombreEstadoUp = findViewById(R.id.etNombreEstado)

        btnBack.setOnClickListener {
            val intentBack = Intent(this, EstadosActivity::class.java)
            startActivity(intentBack)
        }

        //obtenemos datos
        val id = intent.getIntExtra("id_estado", 0)
        val nombreEstado = intent.getStringExtra("nombreEstado")
        val estado = id?.let { Estado(it.toInt(), nombreEstado) }

        etNombreEstadoUp.setText(nombreEstado.toString())

        //actualizar
        btnUpdateEstado.setOnClickListener {
            if(estado != null){
                updateEstado(id)


            } else {
                Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show()

            }
        }


    }

    private fun updateEstado(id:Int){
        val nombreEstado = etNombreEstadoUp.text.toString()
        // estado actualizado
        val estadoActualizado = Estado(id, nombreEstado)
        //acceso a base de datos
        val db = Room.databaseBuilder(
            applicationContext,
            database::class.java, "database"
        ).build()
        val estadodao = db.estadoDAO()
        // actualizar
        CoroutineScope(Dispatchers.IO).launch {
            estadodao.updateEstado(estadoActualizado)
        }

        val intentRegresar = Intent(this, EstadosActivity::class.java)
        startActivity(intentRegresar)

        Toast.makeText(this, "Estado Actualizado con exito!", Toast.LENGTH_SHORT).show()


    }
}