package com.example.myapplication

import android.os.Bundle
import android.os.Environment
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.viewmodel.ProductoViewModel
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import org.apache.poi.xssf.usermodel.XSSFWorkbook
import java.io.File
import java.io.FileOutputStream

class InventarioActivity : BaseActivity() {

    private lateinit var productoViewModel: ProductoViewModel
    private lateinit var adapter: ProductoAdapter
    private var listaCompleta: List<ProductoEntity> = emptyList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_inventario)

        productoViewModel = ViewModelProvider(this)[ProductoViewModel::class.java]

        configurarRecycler()
        configurarBuscador()
        configurarExportar()
        observarProductos()
    }

    private fun configurarRecycler() {
        adapter = ProductoAdapter(emptyList())
        findViewById<RecyclerView>(R.id.recyclerInventario).apply {
            layoutManager = LinearLayoutManager(this@InventarioActivity)
            adapter = this@InventarioActivity.adapter
        }
    }

    private fun observarProductos() {
        productoViewModel.todosLosProductos.observe(this) { productos ->
            listaCompleta = productos
            adapter.actualizarLista(productos)
            actualizarContador(productos.size)
        }
    }

    private fun configurarBuscador() {
        val etBuscar = findViewById<TextInputEditText>(R.id.etBuscar)
        etBuscar.addTextChangedListener(object : android.text.TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val query = s.toString().lowercase()
                val filtrada = listaCompleta.filter {
                    it.nombreProducto.lowercase().contains(query) ||
                            it.modelo.lowercase().contains(query) ||
                            it.codigoBarrasQr.lowercase().contains(query)
                }
                adapter.actualizarLista(filtrada)
                actualizarContador(filtrada.size)
            }
            override fun afterTextChanged(s: android.text.Editable?) {}
        })
    }

    private fun actualizarContador(cantidad: Int) {
        findViewById<TextView>(R.id.tvContador).text = "$cantidad productos encontrados"
    }

    private fun configurarExportar() {
        findViewById<MaterialButton>(R.id.btnExportar).setOnClickListener {
            exportarExcel()
        }
    }

    private fun exportarExcel() {
        try {
            val workbook = XSSFWorkbook()
            val sheet = workbook.createSheet("Inventario")

            // Encabezados
            val headerRow = sheet.createRow(0)
            val headers = listOf("Nombre", "Modelo", "Categoría", "Precio", "Stock", "Stock Mínimo", "Código")
            headers.forEachIndexed { i, titulo -> headerRow.createCell(i).setCellValue(titulo) }

            // Datos
            listaCompleta.forEachIndexed { index, producto ->
                val row = sheet.createRow(index + 1)
                row.createCell(0).setCellValue(producto.nombreProducto)
                row.createCell(1).setCellValue(producto.modelo)
                row.createCell(2).setCellValue(producto.categoria)
                row.createCell(3).setCellValue(producto.precio)
                row.createCell(4).setCellValue(producto.stock.toDouble())
                row.createCell(5).setCellValue(producto.stockMinimo.toDouble())
                row.createCell(6).setCellValue(producto.codigoBarrasQr)
            }

            // Guardar archivo
            val fileName = "inventario_motostore.xlsx"
            val file = File(
                Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS),
                fileName
            )
            FileOutputStream(file).use { workbook.write(it) }
            workbook.close()

            Toast.makeText(this, "Exportado en Descargas: $fileName", Toast.LENGTH_LONG).show()

        } catch (e: Exception) {
            Toast.makeText(this, "Error al exportar: ${e.message}", Toast.LENGTH_LONG).show()
        }
    }
}