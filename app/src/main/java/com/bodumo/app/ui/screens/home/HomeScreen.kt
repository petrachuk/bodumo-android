package com.bodumo.app.ui.screens.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.bodumo.app.domain.model.getDefaultModules
import com.bodumo.app.ui.components.ModuleCard
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale

@Composable
fun HomeScreen(
    onModuleClick: (String) -> Unit = {},
    modifier: Modifier = Modifier
) {
    val modules = remember { getDefaultModules() }

    // Форматирование текущей даты
    val currentDate = remember {
        val formatter = DateTimeFormatter.ofPattern("d MMMM, EEEE", Locale("ru"))
        LocalDate.now().format(formatter)
    }

    LazyColumn(
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        // Приветствие
        item {
            GreetingHeader(date = currentDate)
        }

        // Карточки модулей
        items(
            items = modules,
            key = { it.id }
        ) { module ->
            ModuleCard(
                module = module,
                onClick = { onModuleClick(module.id) }
            )
        }

        // Отступ внизу
        item {
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}

@Composable
private fun GreetingHeader(
    date: String,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        Text(
            text = "Привет!  👋",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier. height(4.dp))

        Text(
            text = date,
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Ваши модули",
            style = MaterialTheme. typography.titleMedium,
            fontWeight = FontWeight.SemiBold
        )
    }
}