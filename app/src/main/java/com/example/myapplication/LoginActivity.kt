package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.myapplication.viewmodel.UsuarioViewModel
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText

class LoginActivity : BaseActivity() {

    private lateinit var usuarioViewModel: UsuarioViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        usuarioViewModel = ViewModelProvider(this)[UsuarioViewModel::class.java]

        // se crean los usuarios establecidos
        usuarioViewModel.inicializarUsuarios()

        // Observar el resultado del login
        usuarioViewModel.loginResultado.observe(this) { usuario ->
            if (usuario != null) {
                Toast.makeText(this, "Bienvenido, ${usuario.nombre}!", Toast.LENGTH_SHORT).show()
                val intent = Intent(this, MainActivity::class.java).apply {
                    putExtra("nombreUsuario", usuario.nombre)
                    putExtra("emailUsuario", usuario.email)
                    putExtra("rangoUsuario", usuario.rango)
                }
                startActivity(intent)
                finish()
            } else {
                Toast.makeText(this, "Usuario o contraseña incorrectos.", Toast.LENGTH_SHORT).show()
            }
        }

        val btnIngresar = findViewById<MaterialButton>(R.id.btnIngresar)
        btnIngresar.setOnClickListener {
            val usuario = findViewById<TextInputEditText>(R.id.etUsuario).text.toString().trim()
            val contrasena = findViewById<TextInputEditText>(R.id.etContrasena).text.toString().trim()

            if (usuario.isEmpty() || contrasena.isEmpty()) {
                Toast.makeText(this, "Por favor ingresa usuario y contraseña.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            usuarioViewModel.login(usuario, contrasena)
        }
    }
}