package com.example.myapplication

import android.os.Bundle
import android.widget.TextView
import com.example.myapplication.BaseActivity

class PerfilActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_perfil)

        val nombre = intent.getStringExtra("nombreUsuario") ?: "Usuario"
        val email = intent.getStringExtra("emailUsuario") ?: "email@example.com"
        val rango = intent.getStringExtra("rangoUsuario") ?: "Empleado"

        // Iniciales para el avatar
        val iniciales = nombre.split(" ")
            .take(2)
            .joinToString("") { it.first().uppercaseChar().toString() }

        findViewById<TextView>(R.id.tvAvatar).text = iniciales
        findViewById<TextView>(R.id.tvNombrePerfil).text = nombre
        findViewById<TextView>(R.id.tvRangoPerfil).text = rango
        findViewById<TextView>(R.id.tvEmailPerfil).text = email
        findViewById<TextView>(R.id.tvUsuarioPerfil).text = nombre.lowercase()
    }
}