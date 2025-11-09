package com.tuapp.organizadorviajes.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.tuapp.organizadorviajes.domain.model.Place
import com.tuapp.organizadorviajes.ui.theme.OrganizadorViajesTheme

@Composable
fun PlaceItem(
    place: Place,
    username: String,
    onPlaceClick: (placeId: String) -> Unit,
    onEditClick: (placeId: String) -> Unit,
    onDeleteClick: (placeId: String) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
            .clickable { onPlaceClick(place.id) }
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(place.imageUrl)
                    .crossfade(true)
                    .build(),
                contentDescription = place.name,
                contentScale = ContentScale.Crop,
                modifier = Modifier.size(64.dp)
            )

            Spacer(modifier = Modifier.width(16.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = place.name,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = place.city,
                    fontSize = 14.sp
                )
            }

            IconButton(onClick = { onEditClick(place.id) }) {
                Icon(Icons.Default.Edit, contentDescription = "Editar Lugar")
            }
            IconButton(onClick = { onDeleteClick(place.id) }) {
                Icon(Icons.Default.Delete, contentDescription = "Eliminar Lugar")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PlaceItemPreview() {
    OrganizadorViajesTheme {
        PlaceItem(
            place = Place(
                id = "1",
                name = "Disney World",
                city = "Orlando",
                description = "",
                imageUrl = "https://example.com/image.jpg",
                timeToSpend = "",
                price = "",
                directions = ""
            ),
            username = "pepito",
            onPlaceClick = {},
            onEditClick = {},
            onDeleteClick = {}
        )
    }
}