package com.tuapp.organizadorviajes.data.remote.dto

import com.google.gson.annotations.SerializedName
import com.tuapp.organizadorviajes.domain.model.Place

data class PlaceDto(
    val id: Int,
    val name: String,
    val city: String,
    @SerializedName("trip_id") val tripId: Int,
    val description: String?,
    @SerializedName("image_url") val imageUrl: String?,
    @SerializedName("time_to_spend") val timeToSpend: String?,
    val price: String?,
    val directions: String?
) {
    fun toDomainModel(): Place {
        return Place(
            id = id.toString(),
            name = name,
            city = city,
            description = description,
            imageUrl = imageUrl,
            timeToSpend = timeToSpend,
            price = price,
            directions = directions
        )
    }
}

data class PlaceRequest(
    val name: String,
    val city: String,
    @SerializedName("trip_id") val tripId: Int,
    val description: String?,
    @SerializedName("image_url") val imageUrl: String?,
    @SerializedName("time_to_spend") val timeToSpend: String?,
    val price: String?,
    val directions: String?
)