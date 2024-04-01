package com.loginapp.recomendia.fragments.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.loginapp.recomendia.adapters.CategoriasAdapter
import com.loginapp.recomendia.entities.Categoria
import com.loginapp.recomendia.repository.CategoriasRepository
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Response
import javax.security.auth.callback.Callback

class CategoriasViewModel : ViewModel() {
    private val categoriasRepository: CategoriasRepository = CategoriasRepository;
    var liveData = MutableLiveData<MutableList<Categoria>>()

    fun getCategorias() {
        viewModelScope.launch {
            categoriasRepository.obtenerCategorias().onSuccess {
                liveData.value = it
            }.onFailure {
                // Manejar error
            }
        }
    }
}