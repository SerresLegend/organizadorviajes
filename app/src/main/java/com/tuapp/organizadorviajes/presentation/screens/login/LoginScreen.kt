package com.tuapp.organizadorviajes.presentation.screens.login

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.tuapp.organizadorviajes.presentation.components.CustomButton
import com.tuapp.organizadorviajes.presentation.components.CustomTextField
import com.tuapp.organizadorviajes.ui.theme.OrganizadorViajesTheme

@Composable
fun LoginScreen(
    viewModel: LoginViewModel = hiltViewModel(),
    onLoginSuccess: (username: String) -> Unit
) {
    val username by viewModel.username.collectAsState()

    Scaffold { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 32.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Ingresá tu usuario:",
                fontSize = 18.sp
            )

            Spacer(modifier = Modifier.height(16.dp))

            CustomTextField(
                value = username,
                onValueChange = viewModel::onUsernameChange,
                label = "Usuario"
            )

            Spacer(modifier = Modifier.height(24.dp))

            CustomButton(
                text = "Ingresar",
                onClick = {
                    onLoginSuccess(username)
                },
                enabled = username.isNotBlank()
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LoginScreenPreview() {
    OrganizadorViajesTheme {
        LoginScreen(onLoginSuccess = {})
    }
}