package com.bodumo.app.ui.screens.modules.workout

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.DirectionsRun
import androidx.compose.material.icons.filled.DirectionsBike
import androidx.compose.material.icons.filled.LocalFireDepartment
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material.icons.filled.Speed
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.bodumo.app.domain.model.Workout
import com.bodumo.app.domain.model.WorkoutType
import com.bodumo.app.domain.model.getDefaultWorkouts
import com.bodumo.app.ui.theme.WorkoutOrange
import java.time.format.DateTimeFormatter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WorkoutScreen(
    onBackClick: () -> Unit,
    onStartWorkout: (WorkoutType) -> Unit = {},
    modifier: Modifier = Modifier
) {
    val workoutHistory = remember { getDefaultWorkouts() }

    // Статистика за неделю
    val weekWorkouts = workoutHistory.size
    val weekDuration = workoutHistory.sumOf { it.durationSeconds }
    val weekCalories = workoutHistory.sumOf { it. calories ?: 0 }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Тренировки") },
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
            // Статистика недели
            item {
                WeekStatisticsCard(
                    workoutsCount = weekWorkouts,
                    totalSeconds = weekDuration,
                    totalCalories = weekCalories
                )
            }

            // Начать тренировку
            item {
                Text(
                    text = "Начать тренировку",
                    style = MaterialTheme. typography.titleMedium,
                    fontWeight = FontWeight.SemiBold
                )
            }

            item {
                LazyRow(
                    horizontalArrangement = Arrangement. spacedBy(12.dp)
                ) {
                    items(WorkoutType.entries) { type ->
                        WorkoutTypeCard(
                            type = type,
                            onClick = { onStartWorkout(type) }
                        )
                    }
                }
            }

            // История
            item {
                Spacer(modifier = Modifier. height(8.dp))
                Text(
                    text = "История",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold
                )
            }

            items(workoutHistory) { workout ->
                WorkoutHistoryItem(workout = workout)
            }

            item {
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    }
}

/**
 * Карточка статистики за неделю.
 */
@Composable
private fun WeekStatisticsCard(
    workoutsCount: Int,
    totalSeconds: Int,
    totalCalories: Int,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier. fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = WorkoutOrange. copy(alpha = 0.1f)
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp)
        ) {
            Text(
                text = "Эта неделя",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold
            )

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier. fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                StatItem(
                    icon = Icons.AutoMirrored.Filled. DirectionsRun,
                    value = "$workoutsCount",
                    label = "тренировок"
                )

                StatItem(
                    icon = Icons. Filled.Schedule,
                    value = formatDuration(totalSeconds),
                    label = "время"
                )

                StatItem(
                    icon = Icons.Filled.LocalFireDepartment,
                    value = "$totalCalories",
                    label = "ккал"
                )
            }
        }
    }
}

@Composable
private fun StatItem(
    icon: ImageVector,
    value: String,
    label: String,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = WorkoutOrange,
            modifier = Modifier.size(24.dp)
        )

        Spacer(modifier = Modifier.height(4.dp))

        Text(
            text = value,
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
            color = WorkoutOrange
        )

        Text(
            text = label,
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

/**
 * Карточка типа тренировки.
 */
@Composable
private fun WorkoutTypeCard(
    type: WorkoutType,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        onClick = onClick,
        modifier = modifier. width(140.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = type.emoji,
                style = MaterialTheme.typography.displaySmall
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = type.displayName,
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Medium
            )

            if (type.hasGps) {
                Text(
                    text = "с GPS",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Icon(
                imageVector = Icons. Filled.PlayArrow,
                contentDescription = "Начать",
                tint = WorkoutOrange,
                modifier = Modifier.size(32.dp)
            )
        }
    }
}

/**
 * Элемент истории тренировок.
 */
@Composable
private fun WorkoutHistoryItem(
    workout: Workout,
    modifier: Modifier = Modifier
) {
    val dateFormatter = remember { DateTimeFormatter.ofPattern("d MMM, HH:mm") }

    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Иконка типа
            Text(
                text = workout.type.emoji,
                style = MaterialTheme.typography.headlineMedium
            )

            Spacer(modifier = Modifier.width(12.dp))

            // Информация
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = workout.type.displayName,
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Medium
                )

                Text(
                    text = workout.startTime.format(dateFormatter),
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            // Показатели
            Column(horizontalAlignment = Alignment.End) {
                Text(
                    text = formatDuration(workout. durationSeconds),
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.SemiBold,
                    color = WorkoutOrange
                )

                workout.distanceMeters?.let { meters ->
                    Text(
                        text = formatDistance(meters),
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme. onSurfaceVariant
                    )
                }

                workout.calories?.let { cal ->
                    Text(
                        text = "$cal ккал",
                        style = MaterialTheme.typography. bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }
    }
}

/**
 * Форматирование длительности.
 */
private fun formatDuration(seconds: Int): String {
    val hours = seconds / 3600
    val minutes = (seconds % 3600) / 60

    return if (hours > 0) {
        "${hours}ч ${minutes}мин"
    } else {
        "${minutes} мин"
    }
}

/**
 * Форматирование дистанции.
 */
private fun formatDistance(meters: Float): String {
    return if (meters >= 1000) {
        String.format("%.1f км", meters / 1000)
    } else {
        "${meters.toInt()} м"
    }
}