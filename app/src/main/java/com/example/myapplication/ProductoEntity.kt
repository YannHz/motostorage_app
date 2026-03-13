package com.example.myapplication

import androidx.room.Entity
import androidx.room.PrimaryKey
@Entity(tableName = "productos")

data class ProductoEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val nombreProducto: String,
    val modelo: String,
    val categoria: String,
    val descripcion: String = "",
    val precio: Double,
    val stock: Int,
    val stockMinimo: Int = 0,
    val codigoBarrasQr: String = ""
)