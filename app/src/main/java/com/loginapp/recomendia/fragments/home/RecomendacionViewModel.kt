package com.loginapp.recomendia.fragments.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.loginapp.recomendia.entities.RecomendacionRequest
import com.loginapp.recomendia.repository.RecomendacionRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class RecomendacionViewModel : ViewModel() {
    private val recomendacionRepository: RecomendacionRepository = RecomendacionRepository();
    var liveData = MutableLiveData<String>()

    fun getRecomendacion(idCat: String, idSub: String, idUser: String, especificaciones: String?) {
        viewModelScope.launch {
            val request : RecomendacionRequest = RecomendacionRequest(idCat, idSub, idUser, especificaciones);
            recomendacionRepository.obtenerRecomendacion(request).onSuccess {
                withContext(Dispatchers.Main) {
                    liveData.value = it.message
                }
            }.onFailure {
                // Manejar error
                println("en el error del getRecomendacion")
                println(it.message)
            }
        }
    }

    fun killViewModel() {
        liveData = MutableLiveData<String>()
    }
}