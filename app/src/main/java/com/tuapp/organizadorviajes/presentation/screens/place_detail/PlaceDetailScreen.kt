package com.tuapp.organizadorviajes.presentation.screens.place_detail

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.tuapp.organizadorviajes.domain.model.Place
import com.tuapp.organizadorviajes.ui.theme.OrganizadorViajesTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlaceDetailScreen(
    viewModel: PlaceDetailViewModel = hiltViewModel(),
    placeId: String,
    onBack: () -> Unit
) {
    val place by viewModel.place.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(place?.name ?: "Cargando...") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Volver"
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            contentAlignment = Alignment.Center
        ) {
            val currentPlace = place
            if (currentPlace == null) {
                CircularProgressIndicator()
            } else {
                PlaceDetailsContent(currentPlace)
            }
        }
    }
}

@Composable
private fun PlaceDetailsContent(place: Place) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(place.imageUrl)
                .crossfade(true)
                .build(),
            contentDescription = place.name,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .height(250.dp)
        )

        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = place.name,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = place.city,
                fontSize = 18.sp,
                style = MaterialTheme.typography.titleMedium
            )

            DetailRow(title = "Descripción", content = place.description)

            DetailRow(title = "Indicaciones", content = place.directions)

            DetailRow(title = "Tiempo recomendado", content = place.timeToSpend)

            DetailRow(title = "Precio", content = place.price)
        }
    }
}

@Composable
private fun DetailRow(title: String, content: String?) {
    if (!content.isNullOrBlank()) {
        Column {
            Text(
                text = title,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )
            Text(
                text = content,
                fontSize = 16.sp,
                style = MaterialTheme.typography.bodyLarge
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PlaceDetailScreenPreview() {
    OrganizadorViajesTheme {

        PlaceDetailScreen(placeId = "1", onBack = {})
    }
}