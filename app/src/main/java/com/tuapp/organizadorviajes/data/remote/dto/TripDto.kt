package com.tuapp.organizadorviajes.data.remote.dto

import com.tuapp.organizadorviajes.domain.model.Trip

data class TripDto(
    val id: Int,
    val name: String,
    val country: String,
    val username: String
) {
    fun toDomainModel(): Trip {
        return Trip(
            id = id.toString(),
            name = name,
            country = country,
            username = username
        )
    }
}

data class TripRequest(
    val name: String,
    val country: String,
    val username: String
)