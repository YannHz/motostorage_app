package com.example.myapplication.repository

import androidx.lifecycle.LiveData
import com.example.myapplication.ProductoDao
import com.example.myapplication.ProductoEntity

class ProductoRepository(private val productoDao: ProductoDao) {

    val todosLosProductos: LiveData<List<ProductoEntity>> = productoDao.obtenerTodosLosProductos()
    val contarProductos: LiveData<Int> = productoDao.contarProductos()
    val stockTotal: LiveData<Int> = productoDao.stockTotal()
    val contarStockBajo: LiveData<Int> = productoDao.contarStockBajo()
    val valorTotal: LiveData<Double> = productoDao.valorTotal()
    val productosStockBajo: LiveData<List<ProductoEntity>> = productoDao.productosStockBajo()

    suspend fun insertar(producto: ProductoEntity) {
        productoDao.registrarProducto(producto)
    }
    suspend fun actualizar(producto: ProductoEntity) {
        productoDao.actualizarProducto(producto)
    }
    suspend fun eliminar(producto: ProductoEntity) {
        productoDao.eliminarProducto(producto)
    }
    suspend fun buscarPorCodigo(codigo: String): ProductoEntity? {
        return productoDao.buscarPorCodigo(codigo)
    }
}