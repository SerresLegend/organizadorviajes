package com.tuapp.organizadorviajes.domain.model

data class Place(
    val id: String,
    val name: String,
    val city: String,
    val description: String?,
    val imageUrl: String?,
    val timeToSpend: String?,
    val price: String?,
    val directions: String?
)