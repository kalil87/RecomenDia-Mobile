package com.loginapp.recomendia.fragments.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.loginapp.recomendia.entities.Categoria
import com.loginapp.recomendia.repository.CategoriasRepository
import kotlinx.coroutines.launch

class SubcategoriasViewModel : ViewModel() {
    private val categoriasRepository: CategoriasRepository = CategoriasRepository;
    var liveData = MutableLiveData<Categoria>()

    fun cargarSubCategorias(id: String){
        viewModelScope.launch {
            categoriasRepository.obtenerCategoria(id).onSuccess {
                liveData.value = it
            }.onFailure {
                // Manejar error
            }
        }
    }
}