package com.example.myapplication

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText

import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch

class RegistrarProductoActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registrar_producto)

        configurarDropdown()
        configurarBotonGuardar()
    }

    private fun configurarDropdown() {
        val categorias = arrayOf("Motos", "Cascos", "Repuestos", "Accesorios")
        val adapter = ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, categorias)
        val dropdown = findViewById<AutoCompleteTextView>(R.id.autoCompleteCategoria)
        dropdown.setAdapter(adapter)
    }

    private fun configurarBotonGuardar() {
        val btnRegistrar = findViewById<MaterialButton>(R.id.btnRegistrarProducto)

        btnRegistrar.setOnClickListener {
            val nombre = findViewById<TextInputEditText>(R.id.etNombre).text.toString().trim()
            val modelo = findViewById<TextInputEditText>(R.id.etModelo).text.toString().trim()
            val categoria = findViewById<AutoCompleteTextView>(R.id.autoCompleteCategoria).text.toString()
            val precioStr = findViewById<TextInputEditText>(R.id.etPrecio).text.toString().trim()
            val descripcion = findViewById<TextInputEditText>(R.id.etDescripcion).text.toString().trim()
            val stockStr = findViewById<TextInputEditText>(R.id.etStock).text.toString().trim()
            val stockMinStr = findViewById<TextInputEditText>(R.id.etStockMinimo).text.toString().trim()
            val codigoBarras = findViewById<TextInputEditText>(R.id.etCodigoBarras).text.toString().trim()

            // validacon básica de campos obligatorios
            if (nombre.isEmpty() || modelo.isEmpty() || precioStr.isEmpty() || stockStr.isEmpty()) {
                Toast.makeText(this, "Por favor llena todos los campos obligatorios (*).", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val precio = precioStr.toDoubleOrNull() ?: 0.0
            val stock = stockStr.toIntOrNull() ?: 0
            val stockMin = stockMinStr.toIntOrNull() ?: 0

            // val nuevoProducto = Producto(nombre, modelo, categoria, precio)
            val nuevoProducto = Producto(
                nombre = nombre,
                modelo = modelo,
                categoria = categoria,
                descripcion = descripcion,
                precio = precio,
                stock = stock,
                stockMinimo = stockMin,
                codigoBarras = codigoBarras
            )

            // aqui es donde el producto está listo para enviarse a la base de datos

            lifecycleScope.launch {
                val database = AppDatabase.getDatabase(this@RegistrarProductoActivity)
                database.productoDao().insertarProducto(nuevoProducto)

                runOnUiThread {
                    Toast.makeText(
                        this@RegistrarProductoActivity,
                        "¡$nombre guardado en MotoStore!",
                        Toast.LENGTH_SHORT
                    ).show()
                    finish() // Cierra la pantalla al terminar
                }
            }
        }
    }
}