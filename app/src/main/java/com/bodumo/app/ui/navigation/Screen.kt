package com.bodumo.app.ui.navigation

/**
 * Sealed class для описания всех экранов приложения.
 * Каждый экран имеет уникальный route для навигации.
 */
sealed class Screen(val route: String) {
    // === Bottom Navigation экраны ===
    data object Home : Screen("home")
    data object Statistics : Screen("statistics")
    data object QuickAction : Screen("quick_action")
    data object Profile :  Screen("profile")
    data object Settings : Screen("settings")

    // === Экраны модулей (добавим позже) ===
    data object WaterDetail : Screen("water_detail")
    data object MedicineDetail : Screen("medicine_detail")
    data object WorkoutDetail : Screen("workout_detail")
    data object AnalysisDetail : Screen("analysis_detail")
}