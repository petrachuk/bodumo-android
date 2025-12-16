package com.bodumo.app.domain.model

import java.time.LocalDate

/**
 * Тестовые данные анализов.
 */
fun getDefaultMetrics(): List<HealthMetric> = listOf(
    // Глюкоза
    HealthMetric(
        id = 1,
        type = MetricType.GLUCOSE,
        value = 5.2f,
        date = LocalDate.now().minusDays(3)
    ),
    HealthMetric(
        id = 2,
        type = MetricType.GLUCOSE,
        value = 5.8f,
        date = LocalDate.now().minusDays(10)
    ),
    HealthMetric(
        id = 3,
        type = MetricType.GLUCOSE,
        value = 4.9f,
        date = LocalDate.now().minusDays(17)
    ),

    // Гемоглобин
    HealthMetric(
        id = 4,
        type = MetricType. HEMOGLOBIN,
        value = 142f,
        date = LocalDate.now().minusDays(5)
    ),
    HealthMetric(
        id = 5,
        type = MetricType.HEMOGLOBIN,
        value = 138f,
        date = LocalDate.now().minusDays(35)
    ),

    // Холестерин
    HealthMetric(
        id = 6,
        type = MetricType.CHOLESTEROL,
        value = 5.8f,
        date = LocalDate.now().minusDays(5),
        notes = "Немного повышен"
    ),

    // Давление
    HealthMetric(
        id = 7,
        type = MetricType. PRESSURE_SYS,
        value = 125f,
        date = LocalDate.now().minusDays(1)
    ),
    HealthMetric(
        id = 8,
        type = MetricType.PRESSURE_DIA,
        value = 82f,
        date = LocalDate.now().minusDays(1)
    ),

    // Пульс
    HealthMetric(
        id = 9,
        type = MetricType.HEART_RATE,
        value = 68f,
        date = LocalDate.now().minusDays(1)
    ),

    // Вес
    HealthMetric(
        id = 10,
        type = MetricType.WEIGHT,
        value = 75.5f,
        date = LocalDate.now().minusDays(1)
    ),
    HealthMetric(
        id = 11,
        type = MetricType.WEIGHT,
        value = 76.0f,
        date = LocalDate.now().minusDays(7)
    ),
    HealthMetric(
        id = 12,
        type = MetricType.WEIGHT,
        value = 76.8f,
        date = LocalDate.now().minusDays(14)
    )
)

/**
 * Группировка метрик по типу с последним значением.
 */
fun List<HealthMetric>.groupedByType(): Map<MetricType, List<HealthMetric>> {
    return this.groupBy { it.type }
        .mapValues { (_, metrics) -> metrics.sortedByDescending { it.date } }
}

/**
 * Получение последнего значения для каждого типа.
 */
fun List<HealthMetric>.latestByType(): Map<MetricType, HealthMetric> {
    return this.groupBy { it.type }
        . mapValues { (_, metrics) -> metrics.maxByOrNull { it.date }!! }
}