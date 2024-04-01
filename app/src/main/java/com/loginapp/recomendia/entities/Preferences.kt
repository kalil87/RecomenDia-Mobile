package com.loginapp.recomendia.entities

import android.content.Context

class Preferences(val context: Context) {
    private val SHARED_NAME = "MY_PREFERENCES"
    private val SHARED_ID = "ID"
    private val SHARED_USERNAME = "USERNAME"
    private val SHARED_EMAIL = "EMAIL"
    private val SHARED_TOKEN = "TOKEN"
    private val SHARED_NATIONALITY = "NATIONALITY"
    val storage = context.getSharedPreferences(SHARED_NAME, Context.MODE_PRIVATE)
    //storage es como local storage, una pequeña memoria cache del celu, archivos pocos pesados, no permite objetos,
    //tiene que ser string o int por ejemplo

    fun guardarUsuario(usuario: Usuario) {
        storage.edit().putString(SHARED_ID, usuario.id).apply()
        storage.edit().putString(SHARED_USERNAME, usuario.name).apply()
        storage.edit().putString(SHARED_EMAIL, usuario.email).apply()
        storage.edit().putString(SHARED_NATIONALITY, usuario.nationality).apply()
        storage.edit().putString(SHARED_TOKEN, usuario.token).apply()
    }

    fun obtenerUsuario(): Usuario {
        return Usuario(
            storage.getString(SHARED_ID, ""),
            storage.getString(SHARED_USERNAME, "")!!,
            storage.getString(SHARED_EMAIL, "")!!,
            null,
            storage.getString(SHARED_NATIONALITY, "")!!,
            storage.getString(SHARED_TOKEN, "")
        )
    }

    fun borrarUsuario() {
        storage.edit().putString(SHARED_ID, null).apply()
        storage.edit().putString(SHARED_USERNAME, null).apply()
        storage.edit().putString(SHARED_EMAIL, null).apply()
        storage.edit().putString(SHARED_NATIONALITY, null).apply()
        storage.edit().putString(SHARED_TOKEN, null).apply()
    }
}