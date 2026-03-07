package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // mision unica de la pantalla para permitir la navegación
        configurarBotonNavegacion()
    }

    private fun configurarBotonNavegacion() {
        // Encontramos el botón azul de "Registrar"
        val btnIrARegistro = findViewById<MaterialButton>(R.id.btnIrARegistro)

        btnIrARegistro.setOnClickListener {
            // lleva a la pantalla de registro
            val intent = Intent(this, RegistrarProductoActivity::class.java)
            startActivity(intent)
        }
    }
}