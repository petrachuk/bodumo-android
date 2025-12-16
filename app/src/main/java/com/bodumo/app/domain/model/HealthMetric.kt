package com.bodumo.app.domain.model

import java.time.LocalDate

/**
 * Тип медицинского анализа.
 */
enum class MetricType(
    val displayName: String,
    val unit: String,
    val normalRange: ClosedFloatingPointRange<Float>?
) {
    GLUCOSE("Глюкоза", "ммоль/л", 3.9f..5.5f),
    HEMOGLOBIN("Гемоглобин", "г/л", 120f..160f),
    CHOLESTEROL("Холестерин общий", "ммоль/л", 3.0f..5.2f),
    CHOLESTEROL_LDL("Холестерин ЛПНП", "ммоль/л", 0f..3.0f),
    CHOLESTEROL_HDL("Холестерин ЛПВП", "ммоль/л", 1.0f..2.2f),
    PRESSURE_SYS("Давление (верхнее)", "мм рт.ст.", 100f..130f),
    PRESSURE_DIA("Давление (нижнее)", "мм рт.ст.", 60f..85f),
    HEART_RATE("Пульс покоя", "уд/мин", 60f..80f),
    WEIGHT("Вес", "кг", null),
    TEMPERATURE("Температура", "°C", 36.1f..37.0f)
}

/**
 * Запись анализа/измерения.
 */
data class HealthMetric(
    val id: Long = 0,
    val type: MetricType,
    val value: Float,
    val date:  LocalDate,
    val notes:  String? = null
) {
    /**
     * Проверка, находится ли значение в норме.
     */
    val isNormal: Boolean?
        get() = type.normalRange?. contains(value)

    /**
     * Статус значения.
     */
    val status: MetricStatus
        get() = when {
            type.normalRange == null -> MetricStatus.UNKNOWN
            value < type.normalRange. start -> MetricStatus.LOW
            value > type.normalRange.endInclusive -> MetricStatus.HIGH
            else -> MetricStatus.NORMAL
        }
}

/**
 * Статус показателя.
 */
enum class MetricStatus {
    NORMAL,     // В норме
    LOW,        // Ниже нормы
    HIGH,       // Выше нормы
    UNKNOWN     // Норма не определена
}