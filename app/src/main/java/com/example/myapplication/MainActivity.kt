package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.button.MaterialButton

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        configurarBotonNavegacion()
        configurarMenuLateral() // Agregamos esta llamada
    }

    private fun configurarMenuLateral() {
        val drawerLayout = findViewById<DrawerLayout>(R.id.drawer_layout)
        val topAppBar = findViewById<MaterialToolbar>(R.id.topAppBar)

        // Cuando presionas el ícono de hamburguesa, se abre el menú
        topAppBar.setNavigationOnClickListener {
            drawerLayout.open()
        }
    }

    private fun configurarBotonNavegacion() {
        val btnIrARegistro = findViewById<MaterialButton>(R.id.btnIrARegistro)
        btnIrARegistro.setOnClickListener {
            val intent = Intent(this, RegistrarProductoActivity::class.java)
            startActivity(intent)
        }
        val btnBuscar = findViewById<MaterialButton>(R.id.btnBuscar)
        btnBuscar.setOnClickListener {
            val intent = Intent(this, InventarioActivity::class.java)
            startActivity(intent)
        }
    }
}