package com.tuapp.organizadorviajes.presentation.screens.all_trips

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.tuapp.organizadorviajes.presentation.components.CustomButton
import com.tuapp.organizadorviajes.presentation.components.TripItem
import com.tuapp.organizadorviajes.ui.theme.OrganizadorViajesTheme

@Composable
fun AllTripsScreen(
    viewModel: AllTripsViewModel = hiltViewModel(),
    username: String,
    onTripClick: (tripId: String, tripName: String) -> Unit,
    onMyTripsClick: () -> Unit
) {
    val trips by viewModel.trips.collectAsState()

    Scaffold(
        bottomBar = {
            Box(
                modifier = Modifier
                    .padding(horizontal = 32.dp)
                    .padding(bottom = 24.dp)
            ) {
                CustomButton(
                    text = "Mis Viajes",
                    onClick = onMyTripsClick
                )
            }
        }
    ) { paddingValues ->

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 16.dp)
        ) {
            items(trips) { trip ->
                TripItem(
                    trip = trip,
                    onTripClick = onTripClick
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AllTripsScreenPreview() {
    OrganizadorViajesTheme {
        AllTripsScreen(
            username = "Test",
            onTripClick = { _,_ -> },
            onMyTripsClick = {}
        )
    }
}