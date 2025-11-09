package com.tuapp.organizadorviajes.presentation.screens.place_form

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tuapp.organizadorviajes.data.remote.dto.PlaceRequest
import com.tuapp.organizadorviajes.domain.repository.TravelRepository
import com.tuapp.organizadorviajes.presentation.navigation.AppScreen
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PlaceFormViewModel @Inject constructor(
    private val repository: TravelRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val tripId: Int = savedStateHandle.get<String>(AppScreen.PlaceForm.ARG_TRIP_ID)!!.toInt()
    private val placeId: String = savedStateHandle.get(AppScreen.PlaceForm.ARG_PLACE_ID)!!
    val isEditing = placeId != "new"

    private val _name = MutableStateFlow("")
    val name = _name.asStateFlow()

    private val _city = MutableStateFlow("")
    val city = _city.asStateFlow()

    private val _description = MutableStateFlow("")
    val description = _description.asStateFlow()

    private val _imageUrl = MutableStateFlow("")
    val imageUrl = _imageUrl.asStateFlow()

    private val _timeToSpend = MutableStateFlow("")
    val timeToSpend = _timeToSpend.asStateFlow()

    private val _price = MutableStateFlow("")
    val price = _price.asStateFlow()

    private val _directions = MutableStateFlow("")
    val directions = _directions.asStateFlow()

    init {
        if (isEditing) {
            viewModelScope.launch {
                val place = repository.getPlaceById(placeId)
                place?.let {
                    _name.value = it.name
                    _city.value = it.city
                    _description.value = it.description ?: ""
                    _imageUrl.value = it.imageUrl ?: ""
                    _timeToSpend.value = it.timeToSpend ?: ""
                    _price.value = it.price ?: ""
                    _directions.value = it.directions ?: ""
                }
            }
        }
    }

    fun onNameChange(value: String) { _name.value = value }
    fun onCityChange(value: String) { _city.value = value }
    fun onDescriptionChange(value: String) { _description.value = value }
    fun onImageUrlChange(value: String) { _imageUrl.value = value }
    fun onTimeChange(value: String) { _timeToSpend.value = value }
    fun onPriceChange(value: String) { _price.value = value }
    fun onDirectionsChange(value: String) { _directions.value = value }


    fun savePlace(onSaveSuccess: () -> Unit) {
        viewModelScope.launch {
            val placeRequest = PlaceRequest(
                name = _name.value,
                city = _city.value,
                tripId = tripId,
                description = _description.value.takeIf { it.isNotBlank() },
                imageUrl = _imageUrl.value.takeIf { it.isNotBlank() },
                timeToSpend = _timeToSpend.value.takeIf { it.isNotBlank() },
                price = _price.value.takeIf { it.isNotBlank() },
                directions = _directions.value.takeIf { it.isNotBlank() }
            )

            if (isEditing) {
                repository.updatePlace(placeId, placeRequest)
            } else {
                repository.createPlace(placeRequest)
            }

            onSaveSuccess()
        }
    }
}