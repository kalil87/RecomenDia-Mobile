package com.loginapp.recomendia.entities

data class Usuario(
    val id: String?,
    val name: String,
    val email: String,
    val password: String?,
    val nationality: String,
    val token: String?
)