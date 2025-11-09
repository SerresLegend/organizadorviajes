package com.tuapp.organizadorviajes.data.remote.repository

import com.tuapp.organizadorviajes.data.remote.TravelApi
import com.tuapp.organizadorviajes.data.remote.dto.PlaceRequest
import com.tuapp.organizadorviajes.data.remote.dto.TripRequest
import com.tuapp.organizadorviajes.domain.model.Place
import com.tuapp.organizadorviajes.domain.model.Trip
import com.tuapp.organizadorviajes.domain.repository.TravelRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class TravelRepositoryImpl @Inject constructor(
    private val api: TravelApi
) : TravelRepository {


    override fun getAllTrips(): Flow<List<Trip>> = flow {
        try {
            emit(api.getAllTrips().map { it.toDomainModel() })
        } catch (e: Exception) {
            e.printStackTrace()
            emit(emptyList())
        }
    }

    override fun getMyTrips(username: String): Flow<List<Trip>> = flow {
        try {
            emit(api.getTripsByUser(username).map { it.toDomainModel() })
        } catch (e: Exception) {
            e.printStackTrace()
            emit(emptyList())
        }
    }

    override suspend fun createTrip(name: String, country: String, username: String): Trip? {
        return try {
            val request = TripRequest(name, country, username)
            api.createTrip(request).toDomainModel()
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    override suspend fun updateTrip(id: String, name: String, country: String, username: String) {
        try {
            val request = TripRequest(name, country, username)
            api.updateTrip(id, request)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override suspend fun deleteTrip(id: String) {
        try {
            api.deleteTrip(id)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }


    override fun getPlacesByTrip(tripId: String): Flow<List<Place>> = flow {
        try {
            emit(api.getPlacesByTrip(tripId).map { it.toDomainModel() })
        } catch (e: Exception) {
            e.printStackTrace()
            emit(emptyList())
        }
    }

    override suspend fun getPlaceById(placeId: String): Place? {
        return try {
            api.getPlaceById(placeId).toDomainModel()
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    override suspend fun createPlace(placeRequest: PlaceRequest): Place? {
        return try {
            api.createPlace(placeRequest).toDomainModel()
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    override suspend fun updatePlace(placeId: String, placeRequest: PlaceRequest) {
        try {
            api.updatePlace(placeId, placeRequest)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override suspend fun deletePlace(placeId: String) {
        try {
            api.deletePlace(placeId)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}