package com.loginapp.recomendia.fragments

import android.text.Editable
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.loginapp.recomendia.entities.ApiError
import com.loginapp.recomendia.entities.LoginParams
import com.loginapp.recomendia.entities.Usuario
import com.loginapp.recomendia.repository.UsuarioRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.HttpException

class LoginViewModel : ViewModel() {
    private val usuarioRepository: UsuarioRepository = UsuarioRepository();
    var liveData = MutableLiveData<Usuario?>()
    var errorLiveData = MutableLiveData<String?>()

    fun validarFormulario(email: Editable, pass: Editable): Boolean {
        return email.isEmpty() || pass.isEmpty()
    }

    fun login(loginParams: LoginParams) {
        println(loginParams)
        errorLiveData.value = null
        viewModelScope.launch {
            usuarioRepository.login(loginParams).onSuccess {
                withContext(Dispatchers.Main) {
                    println(it)
                    println("estoy aca loco")
                    liveData.value = it
                }
            }.onFailure {
                // Manejar error
                val response = it as HttpException
                val message = response.response()?.errorBody()?.string()
                val gson = Gson()
                val apiError = gson.fromJson(message, ApiError::class.java)
                println(apiError)
                errorLiveData.value = apiError.error
            }
        }
    }
}