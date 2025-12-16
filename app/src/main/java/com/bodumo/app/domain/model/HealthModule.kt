package com.bodumo.app.domain.model

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector

/**
 * Модель модуля здоровья для отображения на Dashboard.
 */
data class HealthModule(
    val id: String,
    val name: String,
    val icon:  ImageVector,
    val accentColor: Color,
    val isActive: Boolean = true,
    val sortOrder: Int = 0,
    val summary: ModuleSummary? = null
)

/**
 * Краткая информация о модуле для карточки.
 */
data class ModuleSummary(
    val mainValue: String,
    val mainLabel: String,
    val progress: Float?  = null,  // 0.0 - 1.0
    val progressLabel: String? = null
)