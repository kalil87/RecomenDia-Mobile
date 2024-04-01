package com.loginapp.recomendia.api

import com.loginapp.recomendia.entities.Categoria
import com.loginapp.recomendia.entities.LoginParams
import com.loginapp.recomendia.entities.OlvideContrasena
import com.loginapp.recomendia.entities.Recomendacion
import com.loginapp.recomendia.entities.RecomendacionRequest
import com.loginapp.recomendia.entities.RecuperarParams
import com.loginapp.recomendia.entities.Usuario
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface ApiService {

    @GET("/api/categorias")
    suspend fun getCategorias(): MutableList<Categoria>

    @GET("/api/categorias/{id}")
    suspend fun getCategoria(@Path("id") id: String): Categoria

    @Headers("Content-Type: application/json")
    @POST("/api/recomendacion")
    suspend fun getRecomendacion(@Body request: RecomendacionRequest): Recomendacion

    @GET("/api/recomendacion")
    suspend fun getRecomendaciones(): MutableList<Recomendacion>

    @GET("/api/recomendacion/misRecomendaciones/{id}")
    suspend fun getMisRecomendaciones(@Path("id") id: String): MutableList<Recomendacion>

    @GET("/api/recomendacion/masBuscado")
    suspend fun getMasBuscado(): MutableList<Categoria>

    @Headers("Content-Type: application/json")
    @POST("/api/usuarios")
    suspend fun guardarUsuario(@Body usuario: Usuario): Usuario

    @GET("/api/usuarios/{id}")
    suspend fun getUsuario(@Path("id") id: String): Usuario

    @GET("/api/usuarios/email/{email}")
    suspend fun getUsuarioPorMail(@Path("email") email: String): Usuario

    @Headers("Content-Type: application/json")
    @POST("/api/usuarios/login")
    suspend fun login(@Body loginParams: LoginParams): Usuario

    @Headers("Content-Type: application/json")
    @POST("/api/usuarios/olvide-contrasena")
    suspend fun olvideContrasena(@Body olvideParams: OlvideContrasena): Usuario

    @Headers("Content-Type: application/json")
    @PUT("/api/usuarios/recuperar")
    suspend fun recuperarUsuario(@Body params: RecuperarParams): Usuario
}

