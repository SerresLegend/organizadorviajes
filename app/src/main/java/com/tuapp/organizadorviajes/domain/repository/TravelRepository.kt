package com.tuapp.organizadorviajes.domain.repository

import com.tuapp.organizadorviajes.data.remote.dto.PlaceRequest
import com.tuapp.organizadorviajes.domain.model.Place
import com.tuapp.organizadorviajes.domain.model.Trip
import kotlinx.coroutines.flow.Flow

interface TravelRepository {

    fun getAllTrips(): Flow<List<Trip>>
    fun getMyTrips(username: String): Flow<List<Trip>>
    suspend fun createTrip(name: String, country: String, username: String): Trip?
    suspend fun updateTrip(id: String, name: String, country: String, username: String)
    suspend fun deleteTrip(id: String)

    fun getPlacesByTrip(tripId: String): Flow<List<Place>>
    suspend fun getPlaceById(placeId: String): Place?
    suspend fun createPlace(placeRequest: PlaceRequest): Place?
    suspend fun updatePlace(placeId: String, placeRequest: PlaceRequest)
    suspend fun deletePlace(placeId: String)
}