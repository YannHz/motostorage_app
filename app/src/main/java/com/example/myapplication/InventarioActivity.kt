package com.example.myapplication

import android.os.Bundle
import androidx.appcompat.widget.SearchView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.launch

class InventarioActivity : AppCompatActivity() {
    private lateinit var adapter : ProductoAdapter
    private var listaCompleta: List<Producto> = listOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_inventario)

        setupRecyclerView()
        setupBuscador()
        cargarDatosDesdeDB()
    }

    private fun setupRecyclerView() {
        val recyclerView = findViewById<RecyclerView>(R.id.rvInventario)
        recyclerView.layoutManager = LinearLayoutManager(this)

        adapter = ProductoAdapter(emptyList())
        recyclerView.adapter = adapter
    }

    private fun cargarDatosDesdeDB() {
        lifecycleScope.launch {
            val database = AppDatabase.getDatabase(this@InventarioActivity)
            listaCompleta = database.productoDao().getAllProductos()

            adapter.actualizarLista(listaCompleta)
        }
    }

    private fun setupBuscador() {
        val searchView = findViewById<SearchView>(R.id.svBuscador)

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean = false

            override fun onQueryTextChange(newText: String?): Boolean {
                filtrarProductos(newText)
                return true
            }
        })
    }

    private fun filtrarProductos(texto: String?) {
        val listaFiltrada = if (texto.isNullOrEmpty()) {
            listaCompleta
        } else {
            listaCompleta.filter { producto ->
                producto.nombre.contains(texto, ignoreCase = true) ||
                        producto.modelo.contains(texto, ignoreCase = true) ||
                        (producto.codigoBarras?.contains(texto) ?: false)
            }
        }
        adapter.actualizarLista(listaFiltrada)
    }

}