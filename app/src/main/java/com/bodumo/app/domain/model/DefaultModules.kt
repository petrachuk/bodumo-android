package com.bodumo.app.domain.model

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DirectionsRun
import androidx.compose.material.icons.filled.Medication
import androidx.compose.material.icons.filled.Science
import androidx.compose.material.icons.filled.WaterDrop
import com.bodumo.app.ui.theme.AnalysisCyan
import com.bodumo.app.ui.theme.MedicineViolet
import com.bodumo.app.ui.theme.WaterBlue
import com.bodumo.app.ui.theme.WorkoutOrange

/**
 * Список модулей по умолчанию с тестовыми данными.
 */
fun getDefaultModules(): List<HealthModule> = listOf(
    HealthModule(
        id = "water",
        name = "Вода",
        icon = Icons. Filled.WaterDrop,
        accentColor = WaterBlue,
        isActive = true,
        sortOrder = 0,
        summary = ModuleSummary(
            mainValue = "1.2 л",
            mainLabel = "выпито сегодня",
            progress = 0.6f,
            progressLabel = "1.2 / 2.0 л"
        )
    ),
    HealthModule(
        id = "medicine",
        name = "Лекарства",
        icon = Icons.Filled.Medication,
        accentColor = MedicineViolet,
        isActive = true,
        sortOrder = 1,
        summary = ModuleSummary(
            mainValue = "2 из 3",
            mainLabel = "приёмов сегодня",
            progress = 0.66f,
            progressLabel = "Следующий:  14: 00"
        )
    ),
    HealthModule(
        id = "workout",
        name = "Тренировки",
        icon = Icons. Filled.DirectionsRun,
        accentColor = WorkoutOrange,
        isActive = true,
        sortOrder = 2,
        summary = ModuleSummary(
            mainValue = "3",
            mainLabel = "тренировки на этой неделе",
            progress = null,
            progressLabel = "Последняя: вчера"
        )
    ),
    HealthModule(
        id = "analysis",
        name = "Анализы",
        icon = Icons.Filled.Science,
        accentColor = AnalysisCyan,
        isActive = true,
        sortOrder = 3,
        summary = ModuleSummary(
            mainValue = "5",
            mainLabel = "показателей отслеживается",
            progress = null,
            progressLabel = "Обновлено: 3 дня назад"
        )
    )
)