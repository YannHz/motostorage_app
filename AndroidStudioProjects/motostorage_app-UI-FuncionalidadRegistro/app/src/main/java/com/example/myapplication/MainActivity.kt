package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.button.MaterialButton
import com.google.android.material.navigation.NavigationView

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        configurarBotonNavegacion()
        configurarMenuLateral()
    }

    private fun configurarMenuLateral() {
        val drawerLayout = findViewById<DrawerLayout>(R.id.drawer_layout)
        val topAppBar = findViewById<MaterialToolbar>(R.id.topAppBar)
        val navigationView = findViewById<NavigationView>(R.id.navigation_view)

        // Abrir menú con el ícono hamburguesa
        topAppBar.setNavigationOnClickListener {
            drawerLayout.open()
        }

        // Manejar clics en cada ítem del menú
        navigationView.setNavigationItemSelectedListener { menuItem ->
            drawerLayout.close() // Cerrar el menú primero

            when (menuItem.itemId) {
                R.id.nav_dashboard -> {
                    // Ya estamos aquí, no hacemos nada
                }
                R.id.nav_inventario -> {
                    startActivity(Intent(this, InventarioActivity::class.java))
                }
                R.id.nav_registros -> {
                    startActivity(Intent(this, RegistrarProductoActivity::class.java))
                }
                R.id.nav_estadisticas -> {
                    startActivity(Intent(this, EstadisticasActivity::class.java))
                }
                R.id.nav_perfil -> {
                    startActivity(Intent(this, PerfilActivity::class.java))
                }
                R.id.nav_cerrar_sesion -> {
                    // Volver al login y limpiar el stack
                    val intent = Intent(this, LoginActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(intent)
                }
            }
            true
        }
    }

    private fun configurarBotonNavegacion() {
        val btnIrARegistro = findViewById<MaterialButton>(R.id.btnIrARegistro)
        btnIrARegistro.setOnClickListener {
            startActivity(Intent(this, RegistrarProductoActivity::class.java))
        }
    }
}