package com.example.myapplication.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.myapplication.AppDatabase
import com.example.myapplication.ProductoEntity
import com.example.myapplication.repository.ProductoRepository
import kotlinx.coroutines.launch

class ProductoViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: ProductoRepository
    val todosLosProductos: LiveData<List<ProductoEntity>>

    init {
        val dao = AppDatabase.getDatabase(application).productoDao()
        repository = ProductoRepository(dao)
        todosLosProductos = repository.todosLosProductos
    }

    fun insertar(producto: ProductoEntity) = viewModelScope.launch {
        repository.insertar(producto)
    }

    fun actualizar(producto: ProductoEntity) = viewModelScope.launch {
        repository.actualizar(producto)
    }

    fun eliminar(producto: ProductoEntity) = viewModelScope.launch {
        repository.eliminar(producto)
    }

    suspend fun buscarPorCodigo(codigo: String): ProductoEntity? {
        return repository.buscarPorCodigo(codigo)
    }
}