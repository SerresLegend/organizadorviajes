package com.tuapp.organizadorviajes.presentation.screens.trip_detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tuapp.organizadorviajes.domain.model.Place
import com.tuapp.organizadorviajes.domain.repository.TravelRepository
import com.tuapp.organizadorviajes.presentation.navigation.AppScreen
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TripDetailViewModel @Inject constructor(
    private val repository: TravelRepository,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val tripId = savedStateHandle.getStateFlow(AppScreen.TripDetail.ARG_TRIP_ID, "")

    val tripName = savedStateHandle.getStateFlow(AppScreen.TripDetail.ARG_TRIP_NAME, "Detalle")

    val username = savedStateHandle.getStateFlow(AppScreen.TripDetail.ARG_USERNAME, "")

    val places: StateFlow<List<Place>> = tripId.flatMapLatest { id ->
        repository.getPlacesByTrip(id)
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )

    fun deletePlace(placeId: String) {
        viewModelScope.launch {
            repository.deletePlace(placeId)
        }
    }
}