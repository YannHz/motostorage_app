package com.example.myapplication

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText

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

            // validacon básica de campos obligatorios
            if (nombre.isEmpty() || modelo.isEmpty() || precioStr.isEmpty()) {
                Toast.makeText(this, "por favor llena todos los campos obligatorios (*).", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val precio = precioStr.toDoubleOrNull() ?: 0.0

            // aqui es donde el producto está listo para enviarse a la base de datos

            // val nuevoProducto = Producto(nombre, modelo, categoria, precio)

            Toast.makeText(this, "Producto registrado correctamente.", Toast.LENGTH_SHORT).show()
            finish()
        }
    }
}