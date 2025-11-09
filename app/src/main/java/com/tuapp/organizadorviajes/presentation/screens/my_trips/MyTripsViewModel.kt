package com.tuapp.organizadorviajes.presentation.screens.my_trips

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tuapp.organizadorviajes.domain.model.Trip
import com.tuapp.organizadorviajes.domain.repository.TravelRepository
import com.tuapp.organizadorviajes.presentation.navigation.AppScreen
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MyTripsViewModel @Inject constructor(
    private val repository: TravelRepository,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val username = savedStateHandle.getStateFlow(AppScreen.MyTrips.ARG_USERNAME, "")

    private val _refreshTrigger = MutableStateFlow(0)

    val trips: StateFlow<List<Trip>> = combine(username, _refreshTrigger) { user, _ ->
        user
    }.flatMapLatest { user ->
        if (user.isBlank()) {
            flowOf(emptyList())
        } else {
            repository.getMyTrips(user)
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )

    private val _isDialogVisible = MutableStateFlow(false)
    val isDialogVisible = _isDialogVisible.asStateFlow()
    private val _newTripName = MutableStateFlow("")
    val newTripName = _newTripName.asStateFlow()
    private val _newTripCountry = MutableStateFlow("")
    val newTripCountry = _newTripCountry.asStateFlow()

    fun onShowDialog() {
        _newTripName.value = ""
        _newTripCountry.value = ""
        _isDialogVisible.value = true
    }
    fun onDismissDialog() {
        _isDialogVisible.value = false
    }
    fun onNewTripNameChange(name: String) { _newTripName.value = name }
    fun onNewTripCountryChange(country: String) { _newTripCountry.value = country }


    fun createTrip() {
        viewModelScope.launch {
            repository.createTrip(
                name = _newTripName.value,
                country = _newTripCountry.value,
                username = username.value
            )
            onDismissDialog()
            _refreshTrigger.update { it + 1 }
        }
    }

    fun deleteTrip(tripId: String) {
        viewModelScope.launch {
            repository.deleteTrip(tripId)
            _refreshTrigger.update { it + 1 }
        }
    }
}