package com.loginapp.recomendia.repository

import com.loginapp.recomendia.api.ApiService
import com.loginapp.recomendia.entities.LoginParams
import com.loginapp.recomendia.entities.OlvideContrasena
import com.loginapp.recomendia.entities.RecuperarParams
import com.loginapp.recomendia.entities.Usuario
import okhttp3.OkHttpClient
import retrofit2.HttpException
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.Exception
import java.util.concurrent.TimeUnit

class UsuarioRepository {
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

    suspend fun guardarUsuario(usuario: Usuario): Result<Usuario> {
        return  try {
            val response = apiService.guardarUsuario(usuario)
            Result.success(response)
        } catch (e: HttpException) {
            Result.failure(e)
        }
    }

    suspend fun obtenerUsuario(id: String): Result<Usuario> {
        return  try {
            val response = apiService.getUsuario(id)
            Result.success(response)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun recuperarContraseña(email: OlvideContrasena): Result<Usuario> {
        return try {
            val response = apiService.olvideContrasena(email)
            Result.success(response)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun obtenerUsuarioPorMail(email: String): Result<Usuario> {
        return  try {
            val response = apiService.getUsuarioPorMail(email)
            Result.success(response)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun login(loginParams: LoginParams): Result<Usuario> {
        return try {
            val response = apiService.login(loginParams)
            Result.success(response)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun recuperarUsuario(params: RecuperarParams): Result<Usuario> {
        return  try {
            val response = apiService.recuperarUsuario(params)
            Result.success(response)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
