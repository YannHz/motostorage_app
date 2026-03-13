package com.example.myapplication.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.myapplication.AppDatabase
import com.example.myapplication.UsuarioEntity
import kotlinx.coroutines.launch

class UsuarioViewModel(application: Application) : AndroidViewModel(application) {

    private val usuarioDao = AppDatabase.getDatabase(application).usuarioDao()
    val loginResultado = MutableLiveData<UsuarioEntity?>()
    fun login(usuario: String, contrasena: String) = viewModelScope.launch {
        val resultado = usuarioDao.login(usuario, contrasena)
        loginResultado.postValue(resultado)
    }

    //los usuarios se crean por defecto
    fun inicializarUsuarios() = viewModelScope.launch {
        if (usuarioDao.contarUsuarios() == 0) {
            usuarioDao.insertar(UsuarioEntity(nombre = "Admin", usuario = "Admin", contrasena = "1234", email = "admin@motostore.com", rango = "Administrador"))
            usuarioDao.insertar(UsuarioEntity(nombre = "Juan", usuario = "Juan", contrasena = "1234", email = "juan@motostore.com", rango = "Empleado"))
            usuarioDao.insertar(UsuarioEntity(nombre = "Emanuel", usuario = "Emanuel", contrasena = "1234", email = "emanuel@motostore.com", rango = "Empleado"))
            usuarioDao.insertar(UsuarioEntity(nombre = "Cristian", usuario = "Cristian", contrasena = "1234", email = "cristian@motostore.com", rango = "Empleado"))
        }
    }
}