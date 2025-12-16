package com.bodumo.app.ui.screens.profile

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun ProfileScreen(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            . fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "👤",
            style = MaterialTheme.typography.displayLarge
        )
        Text(
            text = "Профиль",
            style = MaterialTheme. typography.titleLarge
        )
        Text(
            text = "Данные пользователя",
            style = MaterialTheme.typography. bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}