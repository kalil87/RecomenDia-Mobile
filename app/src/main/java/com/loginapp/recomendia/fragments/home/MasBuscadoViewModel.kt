package com.loginapp.recomendia.fragments.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.loginapp.recomendia.entities.Categoria
import com.loginapp.recomendia.entities.Recomendacion
import com.loginapp.recomendia.repository.CategoriasRepository
import com.loginapp.recomendia.repository.RecomendacionRepository
import kotlinx.coroutines.launch

class MasBuscadoViewModel : ViewModel() {
    private val recomendacionRepository: RecomendacionRepository = RecomendacionRepository();
    var liveData = MutableLiveData<MutableList<Categoria>>()

    fun getMasBuscado() {
        viewModelScope.launch {
            recomendacionRepository.masBuscado().onSuccess {
                liveData.value = it
            }.onFailure {
                // Manejar error
            }
        }
    }

    fun killViewModel() {
        liveData = MutableLiveData<MutableList<Categoria>>()
    }
}