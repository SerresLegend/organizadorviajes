package com.tuapp.organizadorviajes.presentation.screens.place_form

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
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
import com.tuapp.organizadorviajes.presentation.components.CustomTextField
import com.tuapp.organizadorviajes.ui.theme.OrganizadorViajesTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlaceFormScreen(
    viewModel: PlaceFormViewModel = hiltViewModel(),
    tripId: String,
    placeId: String,
    onSave: () -> Unit
) {
    val name by viewModel.name.collectAsState()
    val city by viewModel.city.collectAsState()
    val description by viewModel.description.collectAsState()
    val imageUrl by viewModel.imageUrl.collectAsState()
    val timeToSpend by viewModel.timeToSpend.collectAsState()
    val price by viewModel.price.collectAsState()
    val directions by viewModel.directions.collectAsState()

    val isEditing = viewModel.isEditing
    val title = if (isEditing) "Editar Lugar" else "Agregar Lugar"

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(title) },
                navigationIcon = {
                    IconButton(onClick = onSave) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, "Volver")
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
                    text = "Guardar Lugar",
                    onClick = { viewModel.savePlace(onSave) },
                    enabled = name.isNotBlank() && city.isNotBlank()
                )
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 16.dp)
                .verticalScroll(rememberScrollState())
        ) {
            Spacer(Modifier.height(16.dp))

            CustomTextField(value = name, onValueChange = viewModel::onNameChange, label = "Nombre del lugar (obligatorio)")
            Spacer(Modifier.height(8.dp))
            CustomTextField(value = city, onValueChange = viewModel::onCityChange, label = "Ciudad (obligatorio)")

            Spacer(Modifier.height(16.dp))

            CustomTextField(value = description, onValueChange = viewModel::onDescriptionChange, label = "Descripción")
            Spacer(Modifier.height(8.dp))
            CustomTextField(value = imageUrl, onValueChange = viewModel::onImageUrlChange, label = "URL de la Imagen")
            Spacer(Modifier.height(8.dp))
            CustomTextField(value = timeToSpend, onValueChange = viewModel::onTimeChange, label = "Tiempo para pasar")
            Spacer(Modifier.height(8.dp))
            CustomTextField(value = price, onValueChange = viewModel::onPriceChange, label = "Precio")
            Spacer(Modifier.height(8.dp))
            CustomTextField(value = directions, onValueChange = viewModel::onDirectionsChange, label = "Indicaciones")

            Spacer(Modifier.height(16.dp))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PlaceFormScreenPreview() {
    OrganizadorViajesTheme {
        PlaceFormScreen(tripId = "1", placeId = "new", onSave = {})
    }
}