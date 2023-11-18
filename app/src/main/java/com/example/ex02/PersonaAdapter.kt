package com.example.ex02

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.example.ex02.room.Estado
import com.example.ex02.room.Persona
import com.example.ex02.room.database
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class PersonaAdapter(private val listaDePersonas:List<Persona>):
    RecyclerView.Adapter<PersonaAdapter.ViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PersonaAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_persona, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: PersonaAdapter.ViewHolder, position: Int) {
        // para mostrar el contenido de cada posicion del listado
        holder.nombrePersona.text = listaDePersonas[position].nombrePersona
        holder.edadPersona.text = "${listaDePersonas[position].edadPersona} años"

        val context = holder.itemView.context
        val estadoId = listaDePersonas[position].estadoPersona.toString().toInt()
        CoroutineScope(Dispatchers.Main).launch {
            val estado = obtenerNombreEstado(context, estadoId)
            if(estado!= null){
                holder.estadoPersona.text = estado.nombreEstado
            }
        }

        holder.itemView.setOnClickListener {
            val persona = listaDePersonas[position]
            val actualizarIntent = Intent(holder.itemView.context, UpdatePersonaActivity::class.java)
            actualizarIntent.putExtra("id", persona.id)
            actualizarIntent.putExtra("nombrePersona", persona.nombrePersona)
            actualizarIntent.putExtra("edadPersona", persona.edadPersona)
            actualizarIntent.putExtra("estadoPersona", persona.estadoPersona)
            holder.itemView.context.startActivity(actualizarIntent)
        }

        holder.itemView.setOnLongClickListener {
            val persona = listaDePersonas[position]
            mensajeEliminar(holder.itemView.context,persona.id)
            true // Devuelve true para indicar que el evento ha sido manejado

        }
    }

    override fun getItemCount(): Int {
        return listaDePersonas.size
    }

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val nombrePersona:TextView = itemView.findViewById(R.id.nombrePersona)
        val edadPersona: TextView = itemView.findViewById(R.id.edadPersona)
        val estadoPersona: TextView = itemView.findViewById(R.id.estadoPersona)
    }

    fun mensajeEliminar(context: Context, id: Int) {
        val builder = AlertDialog.Builder(context)
        builder.setMessage("Seguro que desea eliminar esta persona")
        builder.setTitle("Eliminar Persona!")
        // Si el usuario da clic en salir
        builder.setPositiveButton("Eliminar") {
                _, _ -> eliminar(context,id)
        }
        // Si el usuario da clic en permanecer
        builder.setNegativeButton("Cancelar") {
                dialog, which -> dialog.cancel()
        }
        val alertDialog = builder.create()
        alertDialog.show()
    }

    fun eliminar(context: Context, id: Int) {
        CoroutineScope(Dispatchers.IO).launch {
            val db = Room.databaseBuilder(
                context.applicationContext,
                database::class.java, "database"
            ).build()

            val personadao = db.personaDAO()
            val persona = personadao.getPersonaById(id)
            if (persona!= null){
                personadao.deletePersona(persona)
            }
            launch(Dispatchers.Main) {
                val intent = Intent(context, PersonasActivity::class.java)
                context.startActivity(intent)
                Toast.makeText(context, "Persona eliminada correctamente", Toast.LENGTH_SHORT).show()
                (context as Activity).finish()
            }
        }
        Toast.makeText(context, "Persona eliminada correctamente", Toast.LENGTH_SHORT).show()

    }

    private suspend fun obtenerNombreEstado(context: Context, estadoId:Int): Estado? {
        return withContext(Dispatchers.IO){
            val db = Room.databaseBuilder(context, database::class.java, "database").build()
            val estadoDAO = db.estadoDAO()
            return@withContext estadoDAO.getEstadoById(estadoId)
        }
    }


}