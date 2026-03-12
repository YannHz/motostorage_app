package com.example.myapplication.repository

import androidx.lifecycle.LiveData
import com.example.myapplication.ProductoDao
import com.example.myapplication.ProductoEntity

class ProductoRepository(private val productoDao: ProductoDao) {

    val todosLosProductos: LiveData<List<ProductoEntity>> = productoDao.obtenerTodosLosProductos()

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