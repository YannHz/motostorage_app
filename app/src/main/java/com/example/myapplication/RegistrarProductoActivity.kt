package com.example.myapplication

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.FrameLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import com.journeyapps.barcodescanner.ScanContract
import com.journeyapps.barcodescanner.ScanOptions
import com.example.myapplication.viewmodel.ProductoViewModel

class RegistrarProductoActivity : AppCompatActivity() {

    private lateinit var productoViewModel: ProductoViewModel

    // scanner ZXING
    private val scannerLauncher = registerForActivityResult(ScanContract()) { result ->
        if (result.contents != null) {
            findViewById<TextInputEditText>(R.id.etCodigoBarras).setText(result.contents)
            Toast.makeText(this, "Código escaneado: ${result.contents}", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, "Escaneo cancelado", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registrar_producto)
        productoViewModel = ViewModelProvider(this)[ProductoViewModel::class.java]

        configurarDropdown()
        configurarBotonEscanear()
        configurarBotonGuardar()
    }

    private fun configurarDropdown() {
        val categorias = arrayOf("Motos", "Cascos", "Repuestos", "Accesorios", "Indumentaria")
        val adapter = ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, categorias)
        val dropdown = findViewById<AutoCompleteTextView>(R.id.autoCompleteCategoria)
        dropdown.setAdapter(adapter)
    }

    private fun configurarBotonEscanear() {
        val frameEscanear = findViewById<FrameLayout>(R.id.frameEscanear)
        frameEscanear.setOnClickListener {
            val options = ScanOptions().apply {
                setPrompt("Apunta la cámara al código")
                setBeepEnabled(true)
                setOrientationLocked(true)
                setBarcodeImageEnabled(false)
            }
            scannerLauncher.launch(options)
        }
    }

    private fun configurarBotonGuardar() {
        val btnRegistrar = findViewById<MaterialButton>(R.id.btnRegistrarProducto)
        btnRegistrar.setOnClickListener {

            //obtener valores de los campos llenados
            val nombre = findViewById<TextInputEditText>(R.id.etNombre).text.toString().trim()
            val modelo = findViewById<TextInputEditText>(R.id.etModelo).text.toString().trim()
            val categoria = findViewById<AutoCompleteTextView>(R.id.autoCompleteCategoria).text.toString().trim()
            val descripcion = findViewById<TextInputEditText>(R.id.etDescripcion).text.toString().trim()
            val precioStr = findViewById<TextInputEditText>(R.id.etPrecio).text.toString().trim()
            val stockStr = findViewById<TextInputEditText>(R.id.etStock).text.toString().trim()
            val stockMinimoStr = findViewById<TextInputEditText>(R.id.etStockMinimo).text.toString().trim()
            val codigo = findViewById<TextInputEditText>(R.id.etCodigoBarras).text.toString().trim()

            //comprobar los campos obligatorios
            if (nombre.isEmpty() || modelo.isEmpty() || precioStr.isEmpty() || stockStr.isEmpty()) {
                Toast.makeText(this, "Por favor llena todos los campos obligatorios (*).", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val precio = precioStr.toDoubleOrNull() ?: 0.0
            val stock = stockStr.toIntOrNull() ?: 0
            val stockMinimo = stockMinimoStr.toIntOrNull() ?: 0
            val nuevoProducto = ProductoEntity(
                nombreProducto = nombre,
                modelo = modelo,
                categoria = categoria,
                descripcion = descripcion,
                precio = precio,
                stock = stock,
                stockMinimo = stockMinimo,
                codigoBarrasQr = codigo
            )

            //guardar en la BD
            productoViewModel.insertar(nuevoProducto)

            Toast.makeText(this, "Producto registrado correctamente.", Toast.LENGTH_SHORT).show()
            limpiarFormulario()
        }
    }

    //limpar los campos
    private fun limpiarFormulario() {
        findViewById<TextInputEditText>(R.id.etNombre).text?.clear()
        findViewById<TextInputEditText>(R.id.etModelo).text?.clear()
        findViewById<AutoCompleteTextView>(R.id.autoCompleteCategoria).text?.clear()
        findViewById<TextInputEditText>(R.id.etDescripcion).text?.clear()
        findViewById<TextInputEditText>(R.id.etPrecio).text?.clear()
        findViewById<TextInputEditText>(R.id.etStock).text?.clear()
        findViewById<TextInputEditText>(R.id.etStockMinimo).text?.clear()
        findViewById<TextInputEditText>(R.id.etCodigoBarras).text?.clear()
    }
}