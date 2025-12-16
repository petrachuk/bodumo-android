package com.bodumo.app.domain.model

import java.time.LocalDateTime

/**
 * Тестовая история тренировок.
 */
fun getDefaultWorkouts(): List<Workout> = listOf(
    Workout(
        id = 1,
        type = WorkoutType.RUN_OUTDOOR,
        startTime = LocalDateTime.now().minusDays(1).withHour(7).withMinute(30),
        endTime = LocalDateTime.now().minusDays(1).withHour(8).withMinute(5),
        durationSeconds = 35 * 60,
        distanceMeters = 5200f,
        calories = 420,
        avgHeartRate = 145
    ),
    Workout(
        id = 2,
        type = WorkoutType. BIKE_INDOOR,
        startTime = LocalDateTime.now().minusDays(3).withHour(18).withMinute(0),
        endTime = LocalDateTime.now().minusDays(3).withHour(18).withMinute(45),
        durationSeconds = 45 * 60,
        distanceMeters = null,
        calories = 380,
        avgHeartRate = 128
    ),
    Workout(
        id = 3,
        type = WorkoutType.RUN_INDOOR,
        startTime = LocalDateTime.now().minusDays(5).withHour(6).withMinute(0),
        endTime = LocalDateTime.now().minusDays(5).withHour(6).withMinute(30),
        durationSeconds = 30 * 60,
        distanceMeters = 4000f,
        calories = 310,
        avgHeartRate = 138
    )
)