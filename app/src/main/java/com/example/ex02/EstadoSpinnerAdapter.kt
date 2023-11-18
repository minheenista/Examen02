package com.example.examen02

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.example.ex02.room.Estado

class EstadoSpinnerAdapter(context: Context, resource: Int, private val estados: List<Estado>) :
    ArrayAdapter<Estado>(context, resource, estados) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = super.getView(position, convertView, parent)
        val textView = view.findViewById<TextView>(android.R.id.text1)
        textView.text = getItem(position)?.nombreEstado ?: ""
        return view
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = super.getDropDownView(position, convertView, parent)
        val textView = view.findViewById<TextView>(android.R.id.text1)
        textView.text = getItem(position)?.nombreEstado ?: ""
        return view
    }
}
