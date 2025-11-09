package com.tuapp.organizadorviajes.data.remote

import com.tuapp.organizadorviajes.data.remote.dto.*
import retrofit2.Response
import retrofit2.http.*

interface TravelApi {


    @GET("trips")
    suspend fun getAllTrips(): List<TripDto>

    @GET("trips/{username}")
    suspend fun getTripsByUser(@Path("username") username: String): List<TripDto>

    @POST("trips")
    suspend fun createTrip(@Body tripRequest: TripRequest): TripDto

    @PUT("trips/{id}")
    suspend fun updateTrip(@Path("id") tripId: String, @Body tripRequest: TripRequest): TripDto

    @DELETE("trips/{id}")
    suspend fun deleteTrip(@Path("id") tripId: String): Response<Unit>


    @GET("trips/{id}/places")
    suspend fun getPlacesByTrip(@Path("id") tripId: String): List<PlaceDto>

    // Asumimos que la API también puede devolver un solo lugar por ID
    @GET("places/{id}")
    suspend fun getPlaceById(@Path("id") placeId: String): PlaceDto

    @POST("places")
    suspend fun createPlace(@Body placeRequest: PlaceRequest): PlaceDto

    @PUT("places/{id}")
    suspend fun updatePlace(@Path("id") placeId: String, @Body placeRequest: PlaceRequest): PlaceDto

    @DELETE("places/{id}")
    suspend fun deletePlace(@Path("id") placeId: String): Response<Unit>
}