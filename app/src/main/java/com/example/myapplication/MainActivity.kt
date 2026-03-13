package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import androidx.drawerlayout.widget.DrawerLayout
import com.example.myapplication.viewmodel.ProductoViewModel
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.navigation.NavigationView

class MainActivity : BaseActivity() {

    private var nombreUsuario: String = "Usuario"
    private var emailUsuario: String = "email@example.com"
    private var rangoUsuario: String = "Empleado"
    private lateinit var productoViewModel: ProductoViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        nombreUsuario = intent.getStringExtra("nombreUsuario") ?: "Usuario"
        emailUsuario = intent.getStringExtra("emailUsuario") ?: "email@example.com"
        rangoUsuario = intent.getStringExtra("rangoUsuario") ?: "Empleado"

        productoViewModel = ViewModelProvider(this)[ProductoViewModel::class.java]

        configurarDashboard()
        configurarMenuLateral()
        observarDatos()
    }

    private fun configurarDashboard() {
        findViewById<TextView>(R.id.tvSaludo)?.text = "Hola,\n$nombreUsuario"
    }

    private fun observarDatos() {
        //total producto
        productoViewModel.contarProductos.observe(this) { total ->
            findViewById<TextView>(R.id.tvTotalProductos)?.text = (total ?: 0).toString()
        }
        //stock total
        productoViewModel.stockTotal.observe(this) { stock ->
            findViewById<TextView>(R.id.tvStockTotal)?.text = (stock ?: 0).toString()
        }
        //stock bajo
        productoViewModel.contarStockBajo.observe(this) { bajo ->
            findViewById<TextView>(R.id.tvStockBajo)?.text = (bajo ?: 0).toString()
        }
        //valor total
        productoViewModel.valorTotal.observe(this) { valor ->
            val v = valor ?: 0.0
            findViewById<TextView>(R.id.tvValorTotal)?.text = "S/.${String.format("%,.0f", v)}"
        }
        //productos con stock bajo
        productoViewModel.productosStockBajo.observe(this) { productos ->
            val contenedor = findViewById<android.widget.LinearLayout>(R.id.contenedorStockBajo)
            contenedor?.removeAllViews()

            if (productos.isNullOrEmpty()) {
                val tv = TextView(this)
                tv.text = "Sin productos con stock bajo"
                tv.setTextColor(0xFF888888.toInt())
                tv.setPadding(0, 8, 0, 8)
                contenedor?.addView(tv)
            } else {
                productos.forEach { producto ->
                    val itemView = layoutInflater.inflate(
                        R.layout.item_stock_bajo, contenedor, false
                    )
                    itemView.findViewById<TextView>(R.id.tvStockBajoNombre).text = producto.nombreProducto
                    itemView.findViewById<TextView>(R.id.tvStockBajoCategoria).text = producto.categoria
                    itemView.findViewById<TextView>(R.id.tvStockBajoUds).text = "${producto.stock} uds"
                    itemView.findViewById<TextView>(R.id.tvStockBajoMin).text = "Min: ${producto.stockMinimo}"
                    contenedor?.addView(itemView)
                }
            }
        }
    }

    private fun configurarMenuLateral() {
        val drawerLayout = findViewById<DrawerLayout>(R.id.drawer_layout)
        val topAppBar = findViewById<MaterialToolbar>(R.id.topAppBar)
        val navigationView = findViewById<NavigationView>(R.id.navigation_view)

        topAppBar.setNavigationOnClickListener { drawerLayout.open() }

        val headerView = navigationView.getHeaderView(0)
        headerView.findViewById<TextView>(R.id.tvNombreHeader)?.text = nombreUsuario

        navigationView.setNavigationItemSelectedListener { menuItem ->
            drawerLayout.close()
            when (menuItem.itemId) {
                R.id.nav_dashboard -> { }
                R.id.nav_inventario -> startActivity(
                    Intent(this, InventarioActivity::class.java)
                )
                R.id.nav_registros -> startActivity(
                    Intent(this, RegistrarProductoActivity::class.java)
                )
                R.id.nav_perfil -> {
                    val intent = Intent(this, PerfilActivity::class.java).apply {
                        putExtra("nombreUsuario", nombreUsuario)
                        putExtra("emailUsuario", emailUsuario)
                        putExtra("rangoUsuario", rangoUsuario)
                    }
                    startActivity(intent)
                }
                R.id.nav_cerrar_sesion -> {
                    val intent = Intent(this, LoginActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(intent)
                }
            }
            true
        }
    }
}