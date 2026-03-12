package com.example.myapplication

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import androidx.room.Delete

@Dao
interface ProductoDao{

    @Insert
    suspend fun insertarProducto( producto: Producto)
    //Obtener productos del mas nuevo al mas viejo
    @Query("SELECT * FROM productos ORDER BY id DESC")
    suspend fun getAllProductos(): List<Producto>

    @Query("SELECT * FROM productos WHERE codigoBarras = :codigo LIMIT 1")
    suspend fun getByCodigo(codigo: String): Producto?

    @Update
    suspend fun update(producto: Producto)

    @Delete
    suspend fun delete(producto: Producto)

    @Query("DELETE FROM productos")
    suspend fun deleteAllProductos()
}