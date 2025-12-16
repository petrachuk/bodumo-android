package com.bodumo.app.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.bodumo.app.domain.model.HealthModule

/**
 * Карточка модуля для Dashboard.
 *
 * @param module - данные модуля
 * @param onClick - callback при нажатии на карточку
 */
@Composable
fun ModuleCard(
    module: HealthModule,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        onClick = onClick,
        modifier = modifier. fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults. cardElevation(
            defaultElevation = 2.dp
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            // Заголовок с иконкой
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = module.icon,
                    contentDescription = module.name,
                    tint = module.accentColor,
                    modifier = Modifier.size(32.dp)
                )

                Spacer(modifier = Modifier.width(12.dp))

                Text(
                    text = module. name,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold
                )
            }

            // Краткая информация
            module.summary?.let { summary ->
                Spacer(modifier = Modifier. height(12.dp))

                // Основное значение
                Row(
                    modifier = Modifier. fillMaxWidth(),
                    horizontalArrangement = Arrangement. SpaceBetween,
                    verticalAlignment = Alignment.Bottom
                ) {
                    Column {
                        Text(
                            text = summary.mainValue,
                            style = MaterialTheme.typography.headlineMedium,
                            fontWeight = FontWeight.Bold,
                            color = module.accentColor
                        )
                        Text(
                            text = summary. mainLabel,
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }

                    summary.progressLabel?.let { label ->
                        Text(
                            text = label,
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme. colorScheme.onSurfaceVariant
                        )
                    }
                }

                // Прогресс-бар (если есть)
                summary.progress?.let { progress ->
                    Spacer(modifier = Modifier. height(12.dp))

                    LinearProgressIndicator(
                        progress = { progress },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(8.dp)
                            .clip(RoundedCornerShape(4.dp)),
                        color = module.accentColor,
                        trackColor = module.accentColor.copy(alpha = 0.2f)
                    )
                }
            }
        }
    }
}