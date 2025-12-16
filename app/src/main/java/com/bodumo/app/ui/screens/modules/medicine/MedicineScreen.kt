package com.bodumo.app.ui.screens.modules.medicine

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
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Medication
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import com.bodumo.app.domain.model.MedicineLog
import com.bodumo.app.domain.model.MedicineStatus
import com.bodumo.app.domain.model.generateTodaySchedule
import com.bodumo.app.domain.model.getDefaultMedicines
import com.bodumo.app.ui.theme.MedicineViolet
import java.time.LocalTime
import java.time.format.DateTimeFormatter

@OptIn(ExperimentalMaterial3Api:: class)
@Composable
fun MedicineScreen(
    onBackClick: () -> Unit,
    onAddClick: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    val medicines = remember { getDefaultMedicines() }
    val schedule = remember {
        mutableStateListOf(*generateTodaySchedule(medicines).toTypedArray())
    }

    val currentTime = LocalTime.now()

    // Статистика
    val totalCount = schedule.size
    val takenCount = schedule.count { it.status == MedicineStatus. TAKEN }
    val pendingCount = schedule.count { it.status == MedicineStatus. PENDING }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Лекарства") },
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
        floatingActionButton = {
            FloatingActionButton(
                onClick = onAddClick,
                containerColor = MedicineViolet
            ) {
                Icon(
                    imageVector = Icons. Filled.Add,
                    contentDescription = "Добавить лекарство",
                    tint = MaterialTheme.colorScheme.onPrimary
                )
            }
        },
        modifier = modifier
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            // Статистика дня
            item {
                DayStatisticsCard(
                    takenCount = takenCount,
                    totalCount = totalCount,
                    pendingCount = pendingCount
                )
            }

            // Ближайший приём
            val nextPending = schedule
                .filter { it.status == MedicineStatus.PENDING }
                .minByOrNull { it.scheduledTime }

            nextPending?.let { next ->
                item {
                    Text(
                        text = "Ближайший приём",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.SemiBold
                    )
                }

                item {
                    NextMedicineCard(
                        log = next,
                        onTakeClick = {
                            val index = schedule.indexOfFirst { it.id == next.id }
                            if (index != -1) {
                                schedule[index] = next. copy(status = MedicineStatus.TAKEN)
                            }
                        },
                        onSkipClick = {
                            val index = schedule.indexOfFirst { it.id == next.id }
                            if (index != -1) {
                                schedule[index] = next.copy(status = MedicineStatus.SKIPPED)
                            }
                        }
                    )
                }
            }

            // Расписание на сегодня
            item {
                Spacer(modifier = Modifier. height(8.dp))
                Text(
                    text = "Расписание на сегодня",
                    style = MaterialTheme. typography.titleMedium,
                    fontWeight = FontWeight.SemiBold
                )
            }

            items(
                items = schedule,
                key = { it.id }
            ) { log ->
                ScheduleItem(
                    log = log,
                    onTakeClick = {
                        val index = schedule.indexOfFirst { it.id == log.id }
                        if (index != -1) {
                            schedule[index] = log.copy(status = MedicineStatus. TAKEN)
                        }
                    },
                    onSkipClick = {
                        val index = schedule.indexOfFirst { it.id == log.id }
                        if (index != -1) {
                            schedule[index] = log.copy(status = MedicineStatus.SKIPPED)
                        }
                    }
                )
            }

            item {
                Spacer(modifier = Modifier.height(80.dp))
            }
        }
    }
}

/**
 * Карточка статистики дня.
 */
@Composable
private fun DayStatisticsCard(
    takenCount: Int,
    totalCount: Int,
    pendingCount: Int,
    modifier: Modifier = Modifier
) {
    val progress = if (totalCount > 0) takenCount.toFloat() / totalCount else 0f

    Card(
        modifier = modifier. fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = MedicineViolet. copy(alpha = 0.1f)
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons. Filled. Medication,
                    contentDescription = null,
                    tint = MedicineViolet,
                    modifier = Modifier.size(28.dp)
                )

                Spacer(modifier = Modifier.width(12.dp))

                Column {
                    Text(
                        text = "Сегодня",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.SemiBold
                    )
                    Text(
                        text = "$takenCount из $totalCount приёмов выполнено",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Прогресс-бар
            androidx.compose.material3.LinearProgressIndicator(
                progress = { progress },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(8.dp),
                color = MedicineViolet,
                trackColor = MedicineViolet.copy(alpha = 0.2f)
            )

            if (pendingCount > 0) {
                Spacer(modifier = Modifier. height(8.dp))

                Text(
                    text = "Осталось: $pendingCount",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}

/**
 * Карточка ближайшего приёма.
 */
@Composable
private fun NextMedicineCard(
    log: MedicineLog,
    onTakeClick: () -> Unit,
    onSkipClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val timeFormatter = remember { DateTimeFormatter.ofPattern("HH:mm") }

    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = MedicineViolet
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Filled.Schedule,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onPrimary,
                    modifier = Modifier.size(20.dp)
                )

                Spacer(modifier = Modifier.width(8.dp))

                Text(
                    text = log. scheduledTime.format(timeFormatter),
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onPrimary,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = log.medicineName,
                style = MaterialTheme.typography.headlineSmall,
                color = MaterialTheme.colorScheme. onPrimary,
                fontWeight = FontWeight. Bold
            )

            Text(
                text = log.dosage,
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onPrimary. copy(alpha = 0.8f)
            )

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier. fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Button(
                    onClick = onTakeClick,
                    modifier = Modifier.weight(1f),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.onPrimary,
                        contentColor = MedicineViolet
                    )
                ) {
                    Icon(
                        imageVector = Icons.Filled.Check,
                        contentDescription = null,
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("Принял")
                }

                OutlinedButton(
                    onClick = onSkipClick,
                    modifier = Modifier.weight(1f),
                    colors = ButtonDefaults.outlinedButtonColors(
                        contentColor = MaterialTheme.colorScheme.onPrimary
                    )
                ) {
                    Icon(
                        imageVector = Icons.Filled.Close,
                        contentDescription = null,
                        modifier = Modifier. size(18.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("Пропустить")
                }
            }
        }
    }
}

/**
 * Элемент расписания.
 */
@Composable
private fun ScheduleItem(
    log: MedicineLog,
    onTakeClick: () -> Unit,
    onSkipClick: () -> Unit,
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
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Время
            Text(
                text = log.scheduledTime.format(timeFormatter),
                style = MaterialTheme. typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = when (log.status) {
                    MedicineStatus.TAKEN -> MaterialTheme.colorScheme.onSurfaceVariant
                    MedicineStatus.SKIPPED -> MaterialTheme.colorScheme.error
                    MedicineStatus.PENDING -> MedicineViolet
                }
            )

            Spacer(modifier = Modifier. width(16.dp))

            // Информация
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = log.medicineName,
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Medium,
                    textDecoration = if (log.status == MedicineStatus.TAKEN) {
                        TextDecoration.LineThrough
                    } else null,
                    color = if (log.status == MedicineStatus.TAKEN) {
                        MaterialTheme.colorScheme.onSurfaceVariant
                    } else {
                        MaterialTheme.colorScheme. onSurface
                    }
                )

                Text(
                    text = log. dosage,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            // Статус / Действия
            when (log.status) {
                MedicineStatus.TAKEN -> {
                    Icon(
                        imageVector = Icons.Filled.Check,
                        contentDescription = "Принято",
                        tint = MaterialTheme.colorScheme.secondary,
                        modifier = Modifier. size(24.dp)
                    )
                }
                MedicineStatus.SKIPPED -> {
                    Icon(
                        imageVector = Icons.Filled.Close,
                        contentDescription = "Пропущено",
                        tint = MaterialTheme.colorScheme.error,
                        modifier = Modifier.size(24.dp)
                    )
                }
                MedicineStatus.PENDING -> {
                    Row(horizontalArrangement = Arrangement. spacedBy(4.dp)) {
                        FilledTonalButton(
                            onClick = onTakeClick,
                            contentPadding = PaddingValues(horizontal = 12.dp, vertical = 4.dp),
                            colors = ButtonDefaults.filledTonalButtonColors(
                                containerColor = MaterialTheme.colorScheme.secondary. copy(alpha = 0.2f),
                                contentColor = MaterialTheme.colorScheme. secondary
                            )
                        ) {
                            Icon(
                                imageVector = Icons.Filled.Check,
                                contentDescription = null,
                                modifier = Modifier.size(16.dp)
                            )
                        }

                        FilledTonalButton(
                            onClick = onSkipClick,
                            contentPadding = PaddingValues(horizontal = 12.dp, vertical = 4.dp),
                            colors = ButtonDefaults.filledTonalButtonColors(
                                containerColor = MaterialTheme.colorScheme.error.copy(alpha = 0.1f),
                                contentColor = MaterialTheme.colorScheme. error
                            )
                        ) {
                            Icon(
                                imageVector = Icons. Filled.Close,
                                contentDescription = null,
                                modifier = Modifier.size(16.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}