package com.loginapp.recomendia.repository

import com.loginapp.recomendia.api.ApiService
import com.loginapp.recomendia.entities.Categoria
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import java.util.concurrent.TimeUnit

object CategoriasRepository {
    private val apiService: ApiService
    private val baseUrl = "http://192.168.0.167:8080/"
    private var categoriasCache: MutableList<Categoria>? = null

    init {
        val okHttpClient = OkHttpClient.Builder()
            .connectTimeout(1, TimeUnit.MINUTES)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(15, TimeUnit.SECONDS)
            .build()
        val retrofit = Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        apiService = retrofit.create(ApiService::class.java)
    }

    suspend fun obtenerCategorias(): Result<MutableList<Categoria>> {
        // Verificar si ya tenemos las categorías en caché
        if (categoriasCache != null) {
            return Result.success(categoriasCache!!)
        }
        return  try {
            val response = apiService.getCategorias()
            // Guardar las categorías en caché
            categoriasCache = response
            Result.success(response)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun obtenerCategoria(id: String): Result<Categoria?> { // agregado ?
        if(categoriasCache != null){// agregadas estas 3 lineas siguientes
            return Result.success(categoriasCache!!.find { it._id == id });// agregado
        }// agregado
        return  try {
            val response = apiService.getCategoria(id)
            Result.success(response)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    fun obtenerNombreCategoria(id: String): String {
        if(categoriasCache != null) {
            val cat = categoriasCache!!.find { it._id == id }
            return cat!!.name
        }
        return ""
    }

    fun obtenerNombreSubcategoria(idCat: String, idSub: String): String {
        if(categoriasCache != null) {
            val cat = categoriasCache!!.find { it._id == idCat }
            return cat!!.subCategories.find { it._id == idSub }!!.name
        }
        return ""
    }
}