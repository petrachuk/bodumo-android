package com.bodumo.app.domain.model

import java.time.LocalTime

/**
 * Модель лекарства.
 */
data class Medicine(
    val id: Long = 0,
    val name:  String,
    val dosage: String,
    val scheduleTimes: List<LocalTime>,
    val notes: String?  = null,
    val isActive: Boolean = true
)

/**
 * Запись о приёме лекарства.
 */
data class MedicineLog(
    val id: Long = 0,
    val medicineId: Long,
    val medicineName: String,
    val dosage: String,
    val scheduledTime: LocalTime,
    val status: MedicineStatus = MedicineStatus.PENDING
)

/**
 * Статус приёма лекарства.
 */
enum class MedicineStatus {
    PENDING,    // Ожидает приёма
    TAKEN,      // Принято
    SKIPPED     // Пропущено
}