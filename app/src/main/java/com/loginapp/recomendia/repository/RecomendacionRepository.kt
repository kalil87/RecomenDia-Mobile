package com.loginapp.recomendia.repository

import com.loginapp.recomendia.api.ApiService
import com.loginapp.recomendia.entities.Categoria
import com.loginapp.recomendia.entities.MiRecomendacionRequest
import com.loginapp.recomendia.entities.Recomendacion
import com.loginapp.recomendia.entities.RecomendacionRequest
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.Exception
import java.util.concurrent.TimeUnit

class RecomendacionRepository() {
    private val apiService: ApiService
    private val baseUrl = "http://192.168.0.167:8080/"

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

    suspend fun obtenerRecomendacion(consulta: RecomendacionRequest): Result<Recomendacion> {
        return  try {
            val response = apiService.getRecomendacion(consulta)
            Result.success(response)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun obtenerRecomendaciones(): Result<MutableList<Recomendacion>> {
        return  try {
            val response = apiService.getRecomendaciones()
            Result.success(response)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun misRecomendaciones(idUser: String): Result<MutableList<Recomendacion>> {
        return  try {
            val response = apiService.getMisRecomendaciones(idUser)
            Result.success(response)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun masBuscado(): Result<MutableList<Categoria>> {
        return  try {
            val response = apiService.getMasBuscado()
            Result.success(response)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}