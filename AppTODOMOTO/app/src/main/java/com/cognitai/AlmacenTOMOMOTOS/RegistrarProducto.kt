package com.cognitai.AlmacenTOMOMOTOS

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class RegistrarProducto : AppCompatActivity() {

    private lateinit var db: AppDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registrar_producto)

        db = AppDatabase.getDatabase(this)

        val btnRegistrar = findViewById<Button>(R.id.btnRegistrar)

        btnRegistrar.setOnClickListener {
            guardarEnBaseDeDatos()
        }
    }

    private fun guardarEnBaseDeDatos() {
        val nombre = findViewById<EditText>(R.id.etNombre).text.toString()
        val precio = findViewById<EditText>(R.id.etPrecio).text.toString().toDoubleOrNull() ?: 0.0
        val stock = findViewById<EditText>(R.id.etStock).text.toString().toIntOrNull() ?: 0
        val codigo = findViewById<EditText>(R.id.etCodigo).text.toString()

        val nuevoProducto = ProductoEntity(
            nombreProducto = nombre,
            modelo = "",
            categoria = "",
            descripcion = "",
            precio = precio,
            stock = stock,
            stockMinimo = 1,
            codigoBarrasQr = codigo
        )

        lifecycleScope.launch(Dispatchers.IO) {
            try {
                db.productoDao().registrarProducto(nuevoProducto)

                withContext(Dispatchers.Main) {
                    Toast.makeText(this@RegistrarProducto, "¡Guardado con éxito!", Toast.LENGTH_SHORT).show()
                    limpiarFormulario()
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    Toast.makeText(this@RegistrarProducto, "Error al guardar", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun limpiarFormulario() {
    }
}