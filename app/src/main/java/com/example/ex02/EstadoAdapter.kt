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
import com.example.ex02.room.database
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class EstadoAdapter(private val listaDeEstados:List<Estado>): RecyclerView.Adapter<EstadoAdapter.ViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EstadoAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_estado, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: EstadoAdapter.ViewHolder, position: Int) {
        // para mostrar el contenido de cada posicion del listado
        holder.nombreEstado.text = listaDeEstados[position].nombreEstado
        // para actualizar la mascota
        holder.itemView.setOnClickListener {
            val estado = listaDeEstados[position]
            val actualizarIntent = Intent(holder.itemView.context, UpdateEstadoActivity::class.java)
            actualizarIntent.putExtra("id_estado", estado.id_estado)
            actualizarIntent.putExtra("nombreEstado", estado.nombreEstado)
            holder.itemView.context.startActivity(actualizarIntent)
        }

        holder.itemView.setOnLongClickListener {
            val estado = listaDeEstados[position]
            mensajeEliminar(holder.itemView.context,estado.id_estado)
            true // Devuelve true para indicar que el evento ha sido manejado

        }
    }

    override fun getItemCount(): Int {
        return listaDeEstados.size
    }

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        //declaracion y referencia de nombre del estado
        val nombreEstado:TextView = itemView.findViewById(R.id.nombreEstado)
    }

    fun mensajeEliminar(context: Context, idEstado: Int) {
        val builder = AlertDialog.Builder(context)
        builder.setMessage("Seguro que desea eliminar este Estado")
        builder.setTitle("Eliminar Estado!")
        // Si el usuario da clic en salir
        builder.setPositiveButton("Eliminar") {
                _, _ -> eliminar(context,idEstado)
        }
        // Si el usuario da clic en permanecer
        builder.setNegativeButton("Cancelar") {
                dialog, which -> dialog.cancel()
        }
        val alertDialog = builder.create()
        alertDialog.show()
    }

    fun eliminar(context: Context, idEstado: Int) {
        CoroutineScope(Dispatchers.IO).launch {
            val db = Room.databaseBuilder(
                context.applicationContext,
                database::class.java, "database"
            ).build()

            // Obten el estadoDao
            val estadodao = db.estadoDAO()

            // Obtén el estado por su ID
            val estado = estadodao.getEstadoById(idEstado)

            // Elimina el estado de la base de datos
            estadodao.deleteEstado(estado)

            launch(Dispatchers.Main) {
                val intent = Intent(context, EstadosActivity::class.java)
                context.startActivity(intent)

                // Usa el contexto correcto para mostrar el Toast
                Toast.makeText(context, "Estado eliminado correctamente", Toast.LENGTH_SHORT).show()

                // Cierra la actividad actual
                (context as Activity).finish()
            }

        }
        Toast.makeText(context, "Estado eliminado correctamente", Toast.LENGTH_SHORT).show()

    }


}