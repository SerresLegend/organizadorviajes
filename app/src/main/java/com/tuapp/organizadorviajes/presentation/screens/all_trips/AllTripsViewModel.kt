package com.tuapp.organizadorviajes.presentation.screens.all_trips

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tuapp.organizadorviajes.domain.model.Trip
import com.tuapp.organizadorviajes.domain.repository.TravelRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class AllTripsViewModel @Inject constructor(
    private val repository: TravelRepository
) : ViewModel() {


    val trips: StateFlow<List<Trip>> = repository.getAllTrips()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )
}