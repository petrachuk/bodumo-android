package com.bodumo.app.domain.model

import java.time.LocalDateTime

/**
 * Тип тренировки.
 */
enum class WorkoutType(
    val displayName: String,
    val emoji: String,
    val hasGps: Boolean
) {
    RUN_OUTDOOR("Бег на улице", "🏃", true),
    RUN_INDOOR("Бег на дорожке", "🏃‍♂️", false),
    BIKE_OUTDOOR("Велосипед на улице", "🚴", true),
    BIKE_INDOOR("Велосипед в зале", "🚴‍♂️", false)
}

/**
 * Модель тренировки.
 */
data class Workout(
    val id: Long = 0,
    val type: WorkoutType,
    val startTime: LocalDateTime,
    val endTime: LocalDateTime?  = null,
    val durationSeconds: Int = 0,
    val distanceMeters: Float?  = null,
    val calories: Int? = null,
    val avgHeartRate: Int? = null
)

/**
 * Состояние активной тренировки.
 */
data class ActiveWorkoutState(
    val type: WorkoutType,
    val isRunning: Boolean = false,
    val isPaused: Boolean = false,
    val elapsedSeconds: Int = 0,
    val distanceMeters: Float = 0f,
    val calories: Int = 0,
    val currentHeartRate: Int?  = null
)