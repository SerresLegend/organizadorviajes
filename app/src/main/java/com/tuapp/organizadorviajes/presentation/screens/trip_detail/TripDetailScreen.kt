package com.tuapp.organizadorviajes.presentation.screens.trip_detail

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.tuapp.organizadorviajes.presentation.components.CustomButton
import com.tuapp.organizadorviajes.presentation.components.PlaceItem
import com.tuapp.organizadorviajes.ui.theme.OrganizadorViajesTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TripDetailScreen(
    viewModel: TripDetailViewModel = hiltViewModel(),
    tripId: String,
    tripName: String,
    username: String,
    onPlaceClick: (placeId: String) -> Unit,
    onAddPlaceClick: () -> Unit,
    onBack: () -> Unit,
    onEditPlaceClick: (placeId: String) -> Unit
) {
    val places by viewModel.places.collectAsState()
    val currentTripName by viewModel.tripName.collectAsState()
    val currentUsername by viewModel.username.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(currentTripName) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Volver"
                        )
                    }
                }
            )
        },
        bottomBar = {
            Box(
                modifier = Modifier
                    .padding(horizontal = 32.dp)
                    .padding(bottom = 24.dp)
            ) {
                CustomButton(
                    text = "Agregar Lugar",
                    onClick = onAddPlaceClick
                )
            }
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 16.dp),
        ) {
            items(places) { place ->
                PlaceItem(
                    place = place,
                    username = currentUsername,
                    onPlaceClick = onPlaceClick,
                    onEditClick = onEditPlaceClick,
                    onDeleteClick = viewModel::deletePlace
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TripDetailScreenPreview() {
    OrganizadorViajesTheme {
        TripDetailScreen(
            tripId = "1",
            tripName = "Ida a Disney",
            username = "Test",
            onPlaceClick = {},
            onAddPlaceClick = {},
            onBack = {},
            onEditPlaceClick = {}
        )
    }
}