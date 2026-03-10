package com.cognitai.AlmacenTOMOMOTOS.BasedeDatos

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
    suspend fun obtenerTodosLosProductos(): List<ProductoEntity>

    @Query("SELECT * FROM productos WHERE codigoBarrasQr = :codigo LIMIT 1")
    suspend fun buscarPorCodigo(codigo: String): ProductoEntity?

    @Update
    suspend fun actualizarProducto(producto: ProductoEntity)

    @Delete
    suspend fun eliminarProducto(producto: ProductoEntity)
}