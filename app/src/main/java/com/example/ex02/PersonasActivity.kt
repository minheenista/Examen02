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
import com.example.ex02.room.Persona
import com.example.ex02.room.database
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PersonasActivity : AppCompatActivity() {

    lateinit var btnBack : Button
    lateinit var btnAddpersona : FloatingActionButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_personas)

        btnBack = findViewById(R.id.btnBack)
        btnAddpersona = findViewById(R.id.AddPersona)

        val db = Room.databaseBuilder(applicationContext, database::class.java, "database").build()

        val personaDAO = db.personaDAO()

        //corutina
        CoroutineScope(Dispatchers.IO).launch {
            //agregamos persona
            //personaDAO.newPersona(Persona(nombrePersona = "Minhee", edadPersona = 21, estadoPersona = 2))

            //listar personas
            val manager = LinearLayoutManager(this@PersonasActivity)
            val decoration = DividerItemDecoration(this@PersonasActivity, manager.orientation)
            val recyclerView = findViewById<RecyclerView>(R.id.recyclerViewPersonas)
            val listadoPersonas : List<Persona> = personaDAO.getAllPersonas()
            runOnUiThread{
                val adapter = PersonaAdapter(listadoPersonas)
                recyclerView.adapter = adapter
                recyclerView.layoutManager = manager
                recyclerView.addItemDecoration(decoration)
            }
        }

        btnBack.setOnClickListener {
            val intentBack = Intent(this, MainActivity::class.java)
            startActivity(intentBack)
        }

        btnAddpersona.setOnClickListener{
            val intentAddpersona = Intent(this, AddPersonaActivity::class.java)
            startActivity(intentAddpersona)
        }
    }
}