package com.cognitai.AlmacenTOMOMOTOS.BasedeDatos

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [ProductoEntity::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun productoDao(): ProductoDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            // Patrón Singleton para evitar que se abran múltiples bases de datos
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "motostore_database" // Nombre del archivo SQLite
                )
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}