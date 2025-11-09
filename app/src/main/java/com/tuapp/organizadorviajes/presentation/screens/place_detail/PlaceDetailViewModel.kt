package com.tuapp.organizadorviajes.presentation.screens.place_detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tuapp.organizadorviajes.domain.model.Place
import com.tuapp.organizadorviajes.domain.repository.TravelRepository
import com.tuapp.organizadorviajes.presentation.navigation.AppScreen
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PlaceDetailViewModel @Inject constructor(
    private val repository: TravelRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val placeId: String = savedStateHandle.get(AppScreen.PlaceDetail.ARG_PLACE_ID)!!

    private val _place = MutableStateFlow<Place?>(null)
    val place = _place.asStateFlow()

    init {
        viewModelScope.launch {
            _place.value = repository.getPlaceById(placeId)
        }
    }
}