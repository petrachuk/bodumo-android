package com.bodumo.app.ui.screens.modules.water

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.WaterDrop
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.bodumo.app.ui.theme.WaterBlue
import java.time.LocalTime
import java.time.format. DateTimeFormatter

/**
 * Экран модуля воды.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WaterScreen(
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    // Состояние
    var totalMl by remember { mutableIntStateOf(1200) }
    val goalMl = 2000
    var customAmount by remember { mutableStateOf("") }

    // История за сегодня
    val history = remember {
        mutableStateListOf(
            WaterEntry(500, LocalTime.of(8, 30)),
            WaterEntry(250, LocalTime.of(10, 15)),
            WaterEntry(250, LocalTime.of(12, 0)),
            WaterEntry(200, LocalTime.of(14, 30))
        )
    }

    // Быстрые кнопки добавления
    val quickAmounts = listOf(250, 500, 750, 1000)

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Вода") },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            imageVector = Icons.AutoMirrored. Filled.ArrowBack,
                            contentDescription = "Назад"
                        )
                    }
                }
            )
        },
        modifier = modifier
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Прогресс
            item {
                WaterProgressCard(
                    currentMl = totalMl,
                    goalMl = goalMl
                )
            }

            // Быстрые кнопки
            item {
                QuickAddButtons(
                    amounts = quickAmounts,
                    onAddClick = { amount ->
                        totalMl += amount
                        history.add(0, WaterEntry(amount, LocalTime.now()))
                    }
                )
            }

            // Произвольный объём
            item {
                CustomAmountInput(
                    value = customAmount,
                    onValueChange = { customAmount = it },
                    onAddClick = {
                        customAmount. toIntOrNull()?.let { amount ->
                            if (amount > 0) {
                                totalMl += amount
                                history.add(0, WaterEntry(amount, LocalTime.now()))
                                customAmount = ""
                            }
                        }
                    }
                )
            }

            // Заголовок истории
            item {
                Text(
                    text = "История за сегодня",
                    style = MaterialTheme.typography. titleMedium,
                    fontWeight = FontWeight.SemiBold
                )
            }

            // Список истории
            items(history) { entry ->
                WaterHistoryItem(entry = entry)
            }

            // Отступ внизу
            item {
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    }
}

/**
 * Карточка с прогрессом.
 */
@Composable
private fun WaterProgressCard(
    currentMl: Int,
    goalMl: Int,
    modifier: Modifier = Modifier
) {
    val progress = (currentMl.toFloat() / goalMl).coerceIn(0f, 1f)
    val percentage = (progress * 100).toInt()

    Card(
        modifier = modifier. fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = WaterBlue.copy(alpha = 0.1f)
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Круговой прогресс
            androidx.compose.foundation.layout.Box(
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(
                    progress = { progress },
                    modifier = Modifier.size(160.dp),
                    strokeWidth = 12.dp,
                    trackColor = WaterBlue. copy(alpha = 0.2f),
                    color = WaterBlue
                )

                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(
                        imageVector = Icons. Filled.WaterDrop,
                        contentDescription = null,
                        tint = WaterBlue,
                        modifier = Modifier.size(32.dp)
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    Text(
                        text = "$percentage%",
                        style = MaterialTheme.typography.headlineMedium,
                        fontWeight = FontWeight.Bold,
                        color = WaterBlue
                    )
                }
            }

            Spacer(modifier = Modifier. height(16.dp))

            // Текст прогресса
            Text(
                text = "${formatMl(currentMl)} / ${formatMl(goalMl)}",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.SemiBold
            )

            Text(
                text = if (currentMl >= goalMl) {
                    "Цель достигнута!  🎉"
                } else {
                    "Осталось: ${formatMl(goalMl - currentMl)}"
                },
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

/**
 * Быстрые кнопки добавления воды.
 */
@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun QuickAddButtons(
    amounts: List<Int>,
    onAddClick: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.fillMaxWidth()) {
        Text(
            text = "Быстрое добавление",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.SemiBold
        )

        Spacer(modifier = Modifier.height(8.dp))

        FlowRow(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement. spacedBy(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            amounts.forEach { amount ->
                FilledTonalButton(
                    onClick = { onAddClick(amount) },
                    colors = ButtonDefaults.filledTonalButtonColors(
                        containerColor = WaterBlue. copy(alpha = 0.15f),
                        contentColor = WaterBlue
                    )
                ) {
                    Icon(
                        imageVector = Icons.Filled.WaterDrop,
                        contentDescription = null,
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(text = formatMl(amount))
                }
            }
        }
    }
}

/**
 * Поле ввода произвольного объёма.
 */
@Composable
private fun CustomAmountInput(
    value: String,
    onValueChange: (String) -> Unit,
    onAddClick:  () -> Unit,
    modifier:  Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            OutlinedTextField(
                value = value,
                onValueChange = { newValue ->
                    // Только цифры
                    if (newValue.all { it. isDigit() }) {
                        onValueChange(newValue)
                    }
                },
                modifier = Modifier.weight(1f),
                label = { Text("Свой объём") },
                suffix = { Text("мл") },
                singleLine = true,
                shape = RoundedCornerShape(12.dp)
            )

            Spacer(modifier = Modifier. width(8.dp))

            Button(
                onClick = onAddClick,
                enabled = value.isNotBlank() && (value.toIntOrNull() ?: 0) > 0,
                colors = ButtonDefaults.buttonColors(
                    containerColor = WaterBlue
                )
            ) {
                Icon(
                    imageVector = Icons.Filled.Add,
                    contentDescription = "Добавить"
                )
            }
        }
    }
}

/**
 * Элемент истории.
 */
@Composable
private fun WaterHistoryItem(
    entry: WaterEntry,
    modifier: Modifier = Modifier
) {
    val timeFormatter = remember { DateTimeFormatter.ofPattern("HH:mm") }

    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons. Filled.WaterDrop,
                contentDescription = null,
                tint = WaterBlue,
                modifier = Modifier.size(24.dp)
            )

            Spacer(modifier = Modifier.width(12.dp))

            Text(
                text = formatMl(entry.amountMl),
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Medium,
                modifier = Modifier.weight(1f)
            )

            Text(
                text = entry.time.format(timeFormatter),
                style = MaterialTheme. typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

/**
 * Данные записи воды.
 */
data class WaterEntry(
    val amountMl: Int,
    val time: LocalTime
)

/**
 * Форматирование миллилитров в литры.
 */
private fun formatMl(ml: Int): String {
    return if (ml >= 1000) {
        val liters = ml / 1000f
        if (liters == liters.toInt().toFloat()) {
            "${liters.toInt()} л"
        } else {
            String.format("%.1f л", liters)
        }
    } else {
        "$ml мл"
    }
}