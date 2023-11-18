package com.example.ex02

import android.app.ActivityOptions
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import android.widget.Button
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    lateinit var btnPersonas: Button
    lateinit var btnEstados: Button
    lateinit var  btnSalir: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val window: Window = window
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.statusBarColor = ContextCompat.getColor(this, androidx.appcompat.R.color.primary_material_light)
        }
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnPersonas = findViewById(R.id.button)
        btnEstados = findViewById(R.id.button2)
        btnSalir = findViewById(R.id.button3)

        btnPersonas.setOnClickListener {
            val intentPersonas = Intent(this, PersonasActivity::class.java )
            startActivity(intentPersonas)
        }

        btnEstados.setOnClickListener {
            val intentEstados = Intent(this, EstadosActivity::class.java )
            startActivity(intentEstados, ActivityOptions.makeSceneTransitionAnimation(this).toBundle())
        }

        btnSalir.setOnClickListener {
            finishAffinity()
        }

    }
}