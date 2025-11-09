package com.tuapp.organizadorviajes.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tuapp.organizadorviajes.domain.model.Trip
import com.tuapp.organizadorviajes.ui.theme.OrganizadorViajesTheme

@Composable
fun TripItem(
    trip: Trip,
    onTripClick: (tripId: String, tripName: String) -> Unit,
    onDeleteClick: ((tripId: String) -> Unit)? = null
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
            .clickable { onTripClick(trip.id, trip.name) }
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = trip.name,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "Creado por: ${trip.username}",
                    fontSize = 12.sp
                )
            }

            Spacer(modifier = Modifier.width(8.dp))

            Text(
                text = trip.country,
                fontSize = 14.sp
            )

            onDeleteClick?.let {
                Spacer(modifier = Modifier.width(8.dp))
                IconButton(onClick = { onDeleteClick(trip.id) }) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = "Eliminar Viaje"
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true, name = "Item Normal")
@Composable
fun TripItemPreview() {
    OrganizadorViajesTheme {
        TripItem(
            trip = Trip("1", "Ida a Disney", "EE.UU", "pepito"),
            onTripClick = { _,_ -> }
        )
    }
}

@Preview(showBackground = true, name = "Item con Botón Eliminar")
@Composable
fun TripItemWithDeletePreview() {
    OrganizadorViajesTheme {
        TripItem(
            trip = Trip("1", "Ida a Disney", "EE.UU", "pepito"),
            onTripClick = { _,_ -> },
            onDeleteClick = {}
        )
    }
}