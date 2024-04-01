package com.loginapp.recomendia.entities

data class Recomendacion(
    val id: String,
    val message: String,
    val categoriaId: String,
    val subcategoriaId: String,
    var categoriaName: String,
    var subCategoriaName: String
)