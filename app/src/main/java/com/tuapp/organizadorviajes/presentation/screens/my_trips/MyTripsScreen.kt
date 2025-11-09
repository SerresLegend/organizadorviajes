package com.tuapp.organizadorviajes.presentation.screens.my_trips

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
import com.tuapp.organizadorviajes.presentation.components.CreateTripDialog
import com.tuapp.organizadorviajes.presentation.components.CustomButton
import com.tuapp.organizadorviajes.presentation.components.TripItem
import com.tuapp.organizadorviajes.ui.theme.OrganizadorViajesTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyTripsScreen(
    viewModel: MyTripsViewModel = hiltViewModel(),
    username: String,
    onTripClick: (tripId: String, tripName: String) -> Unit,
    onBack: () -> Unit
) {
    val trips by viewModel.trips.collectAsState()

    val isDialogVisible by viewModel.isDialogVisible.collectAsState()
    val newTripName by viewModel.newTripName.collectAsState()
    val newTripCountry by viewModel.newTripCountry.collectAsState()

    if (isDialogVisible) {
        CreateTripDialog(
            newTripName = newTripName,
            newTripCountry = newTripCountry,
            onTripNameChange = viewModel::onNewTripNameChange,
            onTripCountryChange = viewModel::onNewTripCountryChange,
            onDismiss = viewModel::onDismissDialog,
            onConfirm = viewModel::createTrip
        )
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Mis Viajes ($username)") },
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
                    text = "Crear Viaje",
                    onClick = viewModel::onShowDialog
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
            items(trips) { trip ->
                TripItem(
                    trip = trip,
                    onTripClick = onTripClick,
                    onDeleteClick = viewModel::deleteTrip
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MyTripsScreenPreview() {
    OrganizadorViajesTheme {
        MyTripsScreen(
            username = "Test",
            onTripClick = { _,_ -> },
            onBack = {}
        )
    }
}