package com.example.myapplication

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface ProductoDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun registrarProducto(producto: ProductoEntity)
    @Query("SELECT * FROM productos ORDER BY nombreProducto ASC")
    fun obtenerTodosLosProductos(): LiveData<List<ProductoEntity>>
    @Query("SELECT * FROM productos WHERE codigoBarrasQr = :codigo LIMIT 1")
    suspend fun buscarPorCodigo(codigo: String): ProductoEntity?
    @Update
    suspend fun actualizarProducto(producto: ProductoEntity)
    @Delete
    suspend fun eliminarProducto(producto: ProductoEntity)

    // para el menu principal
    @Query("SELECT COUNT(*) FROM productos")
    fun contarProductos(): LiveData<Int>

    @Query("SELECT COALESCE(SUM(stock), 0) FROM productos")
    fun stockTotal(): LiveData<Int>

    @Query("SELECT COUNT(*) FROM productos WHERE stock <= stockMinimo")
    fun contarStockBajo(): LiveData<Int>

    @Query("SELECT COALESCE(SUM(precio * stock), 0) FROM productos")
    fun valorTotal(): LiveData<Double>

    @Query("SELECT * FROM productos WHERE stock <= stockMinimo")
    fun productosStockBajo(): LiveData<List<ProductoEntity>>
}