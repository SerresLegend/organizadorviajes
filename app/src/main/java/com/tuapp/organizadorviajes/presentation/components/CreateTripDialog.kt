package com.tuapp.organizadorviajes.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun CreateTripDialog(
    newTripName: String,
    newTripCountry: String,
    onTripNameChange: (String) -> Unit,
    onTripCountryChange: (String) -> Unit,
    onDismiss: () -> Unit,
    onConfirm: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(text = "Crear nuevo viaje") },
        text = {
            Column {
                Text(text = "Ingresa los detalles de tu nuevo viaje.")
                Spacer(Modifier.height(16.dp))

                CustomTextField(
                    value = newTripName,
                    onValueChange = onTripNameChange,
                    label = "Nombre del viaje"
                )
                Spacer(Modifier.height(8.dp))
                CustomTextField(
                    value = newTripCountry,
                    onValueChange = onTripCountryChange,
                    label = "País"
                )
            }
        },
        confirmButton = {
            Row(
                modifier = Modifier.fillMaxWidth().padding(8.dp),
                horizontalArrangement = Arrangement.End
            ) {
                TextButton(onClick = onDismiss) {
                    Text("Cancelar")
                }
                TextButton(
                    onClick = onConfirm,
                    enabled = newTripName.isNotBlank() && newTripCountry.isNotBlank()
                ) {
                    Text("Crear")
                }
            }
        }
    )
}