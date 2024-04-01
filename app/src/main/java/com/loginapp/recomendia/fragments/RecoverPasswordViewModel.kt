package com.loginapp.recomendia.fragments

import android.text.Editable
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.loginapp.recomendia.entities.ApiError
import com.loginapp.recomendia.entities.OlvideContrasena
import com.loginapp.recomendia.entities.Usuario
import com.loginapp.recomendia.repository.UsuarioRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.util.regex.Pattern

class RecoverPasswordViewModel : ViewModel() {
    val emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"
    private val usuarioRepository: UsuarioRepository = UsuarioRepository();
    var liveData = MutableLiveData<String?>()

    fun validarEmail(email: Editable): Boolean {
        return Pattern.matches(emailPattern, email.toString())
    }

    fun enviarRecuperoContraseña(email: Editable): LiveData<Result<Usuario>?> {
        val resultLiveData = MutableLiveData<Result<Usuario>?>()

        viewModelScope.launch {
            try {
                val olvideContrasena = OlvideContrasena(email.toString());
                val recuperarResult = usuarioRepository.recuperarContraseña(olvideContrasena);
                if (recuperarResult != null) {
                    resultLiveData.postValue(recuperarResult)
                } else {
                    resultLiveData.postValue(null)
                }
            } catch (error: Throwable) {
                resultLiveData.postValue(null)
            }
        }
        return resultLiveData
    }
    fun registrarUsuario(email: String): LiveData<Result<Usuario>> {
        val resultLiveData = MutableLiveData<Result<Usuario>>()
        viewModelScope.launch {
            try {
                val usuarioResult = usuarioRepository.obtenerUsuarioPorMail(email)
                println("valor de: $usuarioResult")
                // Verifica si el resultado es exitoso y contiene un usuario
                if (usuarioResult != null) {
                    resultLiveData.postValue(usuarioResult)
                } else {
                    println("Estoy aca")
                    resultLiveData.postValue(Result.failure(Throwable("Usuario no encontrado")))
                }
            } catch (error: Throwable) {
                // Maneja errores, incluyendo errores de red o excepciones inesperadas
                resultLiveData.postValue(Result.failure(error))
            }
        }
        return resultLiveData
    }
}