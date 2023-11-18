package com.example.ex02

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.example.ex02.room.Estado
import com.example.ex02.room.database
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class EstadosActivity : AppCompatActivity() {

    lateinit var btnAddEstado : FloatingActionButton
    lateinit var btnBack : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_estados)

        btnBack = findViewById(R.id.btnBack)
        btnAddEstado = findViewById(R.id.btnAddEstado)

        val db = Room.databaseBuilder(applicationContext, database::class.java, "database").build()

        //Usamos DAO
        val estadoDAO = db.estadoDAO()

        //corutina
        CoroutineScope(Dispatchers.IO).launch {
            //Agregamos estado
           // estadoDAO.newEstado(Estado(nombreEstado = "Hola"))

            //Listar estados
            val manager = LinearLayoutManager(this@EstadosActivity)
            val decoration = DividerItemDecoration(this@EstadosActivity, manager.orientation)
            val recyclerView = findViewById<RecyclerView>(R.id.recyclerViewEstados)
            val listadoEstados : List<Estado> = estadoDAO.getAllEstados()
            runOnUiThread{
                val adapter = EstadoAdapter(listadoEstados)
                recyclerView.adapter = adapter
                recyclerView.layoutManager = manager
                recyclerView.addItemDecoration(decoration)
            }

        }

        btnBack.setOnClickListener{
            val intentBack = Intent(this, MainActivity::class.java)
            startActivity(intentBack)
        }

        btnAddEstado.setOnClickListener{
            val intentAdd = Intent(this, AddEstadoActivity::class.java)
            startActivity(intentAdd)
        }
    }
}