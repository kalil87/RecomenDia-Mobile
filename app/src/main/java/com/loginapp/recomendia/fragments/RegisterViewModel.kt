package com.loginapp.recomendia.fragments

import android.text.Editable
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.loginapp.recomendia.entities.ApiError
import com.loginapp.recomendia.entities.RecomendacionRequest
import com.loginapp.recomendia.entities.Usuario
import com.loginapp.recomendia.repository.RecomendacionRepository
import com.loginapp.recomendia.repository.UsuarioRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.util.regex.Pattern

class RegisterViewModel : ViewModel() {
    val emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"
    private val usuarioRepository: UsuarioRepository = UsuarioRepository();
    var liveData = MutableLiveData<String?>()

    fun validarFormulario(name: Editable, email: Editable, pass: Editable, confPass: Editable, nationality: Editable): Boolean {
        return name.isEmpty() || email.isEmpty() || pass.isEmpty() || confPass.isEmpty() || nationality.isEmpty()
    }

    fun validarEmail(email: Editable): Boolean {
        return Pattern.matches(emailPattern, email.toString())
    }

    fun validarContraseña(password: Editable): Boolean {
        val passwordREGEX = Pattern.compile("^" +
                "(?=.*[0-9])" +         //al menos 1 digito
                "(?=.*[a-z])" +         //al menos 1 minuscula
                "(?=.*[A-Z])" +         //al menos 1 mayuscula
                "(?=.*[a-zA-Z])" +      // todas las letras
                "(?=.*[@#$%^&+=])" +    //al menos 1 caracter especial
                "(?=\\S+$)" +           //sin espacios en blacno
            ".{6,12}" +                 //entre 6 a 12 caracteres
                "$");
        return passwordREGEX.matcher(password).matches()
    }

    fun validarPassword(pass: Editable, confPass: Editable): Boolean{
        return pass.toString() == confPass.toString()
    }

    fun registrarUsuario(user: Usuario) {
        viewModelScope.launch {
            usuarioRepository.guardarUsuario(user).onSuccess {
                withContext(Dispatchers.Main) {
                    liveData.value = ""
                }
            }.onFailure {
                // Manejar error
                val response = it as HttpException
                val message = response.response()?.errorBody()?.string()
                val gson = Gson()
                val apiError = gson.fromJson(message, ApiError::class.java)
                liveData.value = apiError.error
            }
        }
    }
}