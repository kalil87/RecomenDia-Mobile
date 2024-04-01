package com.loginapp.recomendia.entities

data class Categoria(
    val _id: String,
    val name: String,
    val subCategories: MutableList<Categoria>,
    val image: Int,
    val recomendaciones: MutableList<Recomendacion>?,
    val categoriaId: String?
)
