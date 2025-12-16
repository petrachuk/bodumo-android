package com.bodumo.app.domain.model

import java.time. LocalTime

/**
 * Тестовые данные лекарств.
 */
fun getDefaultMedicines(): List<Medicine> = listOf(
    Medicine(
        id = 1,
        name = "Витамин D",
        dosage = "1000 МЕ",
        scheduleTimes = listOf(LocalTime.of(9, 0)),
        notes = "После завтрака"
    ),
    Medicine(
        id = 2,
        name = "Омега-3",
        dosage = "1 капсула",
        scheduleTimes = listOf(LocalTime.of(9, 0), LocalTime.of(21, 0)),
        notes = "Во время еды"
    ),
    Medicine(
        id = 3,
        name = "Магний B6",
        dosage = "2 таблетки",
        scheduleTimes = listOf(LocalTime.of(14, 0), LocalTime.of(21, 0)),
        notes = null
    )
)

/**
 * Генерация расписания на сегодня.
 */
fun generateTodaySchedule(medicines: List<Medicine>): List<MedicineLog> {
    val schedule = mutableListOf<MedicineLog>()
    var logId = 1L

    medicines.filter { it.isActive }.forEach { medicine ->
        medicine.scheduleTimes.forEach { time ->
            schedule.add(
                MedicineLog(
                    id = logId++,
                    medicineId = medicine.id,
                    medicineName = medicine.name,
                    dosage = medicine.dosage,
                    scheduledTime = time,
                    status = MedicineStatus. PENDING
                )
            )
        }
    }

    return schedule. sortedBy { it.scheduledTime }
}