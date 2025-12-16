package com.bodumo.app.ui.screens.modules.analysis

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
import androidx.compose.material.icons.automirrored.filled.TrendingDown
import androidx.compose.material.icons.automirrored.filled.TrendingUp
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Error
import androidx.compose.material.icons.filled.Science
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.bodumo.app.domain.model.HealthMetric
import com.bodumo.app.domain.model.MetricStatus
import com.bodumo.app.domain.model.MetricType
import com.bodumo.app.domain.model.getDefaultMetrics
import com.bodumo.app.domain.model.latestByType
import com.bodumo.app.ui.theme.AnalysisCyan
import java.time.format.DateTimeFormatter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AnalysisScreen(
    onBackClick:  () -> Unit,
    onAddClick: () -> Unit = {},
    onMetricClick: (MetricType) -> Unit = {},
    modifier: Modifier = Modifier
) {
    val allMetrics = remember { getDefaultMetrics() }
    val latestMetrics = remember { allMetrics.latestByType() }

    // Статистика
    val totalTypes = latestMetrics.size
    val normalCount = latestMetrics.values.count { it.status == MetricStatus.NORMAL }
    val warningCount = latestMetrics. values.count {
        it.status == MetricStatus.HIGH || it.status == MetricStatus.LOW
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Анализы") },
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
                containerColor = AnalysisCyan
            ) {
                Icon(
                    imageVector = Icons. Filled.Add,
                    contentDescription = "Добавить запись",
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
            // Сводка
            item {
                SummaryCard(
                    totalTypes = totalTypes,
                    normalCount = normalCount,
                    warningCount = warningCount
                )
            }

            // Заголовок списка
            item {
                Spacer(modifier = Modifier. height(8.dp))
                Text(
                    text = "Показатели",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold
                )
            }

            // Список показателей
            items(latestMetrics.entries. toList()) { (type, metric) ->
                MetricCard(
                    metric = metric,
                    onClick = { onMetricClick(type) }
                )
            }

            item {
                Spacer(modifier = Modifier.height(80.dp))
            }
        }
    }
}

/**
 * Карточка сводки.
 */
@Composable
private fun SummaryCard(
    totalTypes: Int,
    normalCount: Int,
    warningCount: Int,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier. fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = AnalysisCyan.copy(alpha = 0.1f)
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
                    imageVector = Icons. Filled.Science,
                    contentDescription = null,
                    tint = AnalysisCyan,
                    modifier = Modifier.size(28.dp)
                )

                Spacer(modifier = Modifier.width(12.dp))

                Text(
                    text = "Обзор показателей",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier. fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                SummaryItem(
                    value = "$totalTypes",
                    label = "показателей",
                    color = AnalysisCyan
                )

                SummaryItem(
                    value = "$normalCount",
                    label = "в норме",
                    color = MaterialTheme.colorScheme.secondary
                )

                if (warningCount > 0) {
                    SummaryItem(
                        value = "$warningCount",
                        label = "отклонений",
                        color = MaterialTheme.colorScheme.error
                    )
                }
            }
        }
    }
}

@Composable
private fun SummaryItem(
    value: String,
    label: String,
    color: androidx.compose.ui.graphics.Color,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = value,
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
            color = color
        )

        Text(
            text = label,
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

/**
 * Карточка показателя.
 */
@Composable
private fun MetricCard(
    metric: HealthMetric,
    onClick: () -> Unit,
    modifier:  Modifier = Modifier
) {
    val dateFormatter = remember { DateTimeFormatter. ofPattern("d MMM yyyy") }

    Card(
        onClick = onClick,
        modifier = modifier. fillMaxWidth(),
        shape = RoundedCornerShape(12.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Индикатор статуса
            StatusIcon(status = metric.status)

            Spacer(modifier = Modifier.width(12.dp))

            // Информация
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = metric.type.displayName,
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Medium
                )

                Text(
                    text = metric. date.format(dateFormatter),
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )

                metric.notes?.let { notes ->
                    Text(
                        text = notes,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme. colorScheme.onSurfaceVariant
                    )
                }
            }

            // Значение
            Column(horizontalAlignment = Alignment.End) {
                Row(verticalAlignment = Alignment.Bottom) {
                    Text(
                        text = formatValue(metric.value),
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        color = when (metric.status) {
                            MetricStatus.NORMAL -> MaterialTheme.colorScheme.secondary
                            MetricStatus.HIGH -> MaterialTheme.colorScheme.error
                            MetricStatus.LOW -> MaterialTheme.colorScheme.tertiary
                            MetricStatus.UNKNOWN -> MaterialTheme.colorScheme.onSurface
                        }
                    )

                    Spacer(modifier = Modifier. width(4.dp))

                    Text(
                        text = metric.type.unit,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }

                // Референсные значения
                metric.type.normalRange?.let { range ->
                    Text(
                        text = "Норма: ${formatValue(range.start)}–${formatValue(range.endInclusive)}",
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme. onSurfaceVariant
                    )
                }
            }
        }
    }
}

/**
 * Иконка статуса.
 */
@Composable
private fun StatusIcon(
    status: MetricStatus,
    modifier: Modifier = Modifier
) {
    val (icon, color) = when (status) {
        MetricStatus. NORMAL -> Icons. Filled.CheckCircle to MaterialTheme.colorScheme.secondary
        MetricStatus.HIGH -> Icons.AutoMirrored.Filled. TrendingUp to MaterialTheme. colorScheme.error
        MetricStatus.LOW -> Icons.AutoMirrored.Filled. TrendingDown to MaterialTheme.colorScheme.tertiary
        MetricStatus.UNKNOWN -> Icons.Filled.Science to MaterialTheme.colorScheme.onSurfaceVariant
    }

    Icon(
        imageVector = icon,
        contentDescription = status.name,
        tint = color,
        modifier = modifier.size(24.dp)
    )
}

/**
 * Форматирование значения.
 */
private fun formatValue(value: Float): String {
    return if (value == value.toLong().toFloat()) {
        value.toLong().toString()
    } else {
        String.format("%.1f", value)
    }
}