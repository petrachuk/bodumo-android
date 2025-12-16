package com.bodumo.app.ui.screens.statistics

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
fun StatisticsScreen(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            . fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "📊",
            style = MaterialTheme. typography.displayLarge
        )
        Text(
            text = "Статистика",
            style = MaterialTheme.typography. titleLarge
        )
        Text(
            text = "Графики и аналитика",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}