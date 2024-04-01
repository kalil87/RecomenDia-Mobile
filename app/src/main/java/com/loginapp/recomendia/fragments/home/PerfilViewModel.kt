package com.loginapp.recomendia.fragments.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.loginapp.recomendia.entities.Categoria
import com.loginapp.recomendia.entities.Preferences
import com.loginapp.recomendia.entities.Recomendacion
import com.loginapp.recomendia.repository.CategoriasRepository
import com.loginapp.recomendia.repository.RecomendacionRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class PerfilViewModel : ViewModel() {
    private val recomendacionRepository: RecomendacionRepository = RecomendacionRepository();
    private val categoriasRepository: CategoriasRepository = CategoriasRepository;
    var liveData = MutableLiveData<MutableList<Recomendacion>>()

    fun getMisRecomendaciones(idUser: String) {
        viewModelScope.launch {
            recomendacionRepository.misRecomendaciones(idUser).onSuccess { recomendaciones ->
                withContext(Dispatchers.Main) {
                    for (recomendacion in recomendaciones) {
                        categoriasRepository.obtenerCategoria(recomendacion.categoriaId).onSuccess { categoria ->
                            recomendacion.categoriaName = categoria!!.name // agregado !!
                            var subcategoria = categoria.subCategories.find { it._id == recomendacion.subcategoriaId }
                            if (subcategoria != null) {
                                recomendacion.subCategoriaName = subcategoria.name
                            }
                        }
                    }
                    liveData.value = recomendaciones
                }
            }.onFailure {
                // Manejar error
                println(it.message)
            }
        }
    }

    fun killViewModel() {
        liveData = MutableLiveData<MutableList<Recomendacion>>()
    }
}