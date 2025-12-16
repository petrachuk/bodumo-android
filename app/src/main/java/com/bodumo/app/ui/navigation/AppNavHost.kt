package com.bodumo.app.ui.navigation

import androidx.compose.runtime.Composable
import androidx. compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation. compose.NavHost
import androidx. navigation.compose.composable
import com.bodumo.app. ui.screens.home.HomeScreen
import com.bodumo.app.ui.screens.modules.analysis.AnalysisScreen
import com.bodumo.app.ui.screens. modules.medicine.MedicineScreen
import com.bodumo.app.ui.screens.modules.water.WaterScreen
import com.bodumo.app.ui.screens.modules.workout.WorkoutScreen
import com.bodumo.app.ui.screens.profile.ProfileScreen
import com.bodumo.app.ui.screens.settings. SettingsScreen
import com.bodumo.app.ui.screens.statistics.StatisticsScreen

@Composable
fun AppNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Home.route,
        modifier = modifier
    ) {
        // Главная
        composable(route = Screen.Home.route) {
            HomeScreen(
                onModuleClick = { moduleId ->
                    when (moduleId) {
                        "water" -> navController.navigate(Screen.WaterDetail.route)
                        "medicine" -> navController.navigate(Screen.MedicineDetail.route)
                        "workout" -> navController.navigate(Screen.WorkoutDetail.route)
                        "analysis" -> navController.navigate(Screen.AnalysisDetail.route)
                    }
                }
            )
        }

        // Статистика
        composable(route = Screen.Statistics.route) {
            StatisticsScreen()
        }

        // Быстрое действие
        composable(route = Screen.QuickAction.route) {
            HomeScreen(
                onModuleClick = { moduleId ->
                    when (moduleId) {
                        "water" -> navController. navigate(Screen.WaterDetail. route)
                        "medicine" -> navController.navigate(Screen. MedicineDetail.route)
                        "workout" -> navController.navigate(Screen.WorkoutDetail.route)
                        "analysis" -> navController.navigate(Screen.AnalysisDetail.route)
                    }
                }
            )
        }

        // Профиль
        composable(route = Screen. Profile.route) {
            ProfileScreen()
        }

        // Настройки
        composable(route = Screen.Settings.route) {
            SettingsScreen()
        }

        // === Экраны модулей ===

        // Модуль воды
        composable(route = Screen.WaterDetail.route) {
            WaterScreen(
                onBackClick = { navController.popBackStack() }
            )
        }

        // Модуль лекарств
        composable(route = Screen.MedicineDetail.route) {
            MedicineScreen(
                onBackClick = { navController.popBackStack() },
                onAddClick = { /* TODO */ }
            )
        }

        // Модуль тренировок
        composable(route = Screen.WorkoutDetail. route) {
            WorkoutScreen(
                onBackClick = { navController.popBackStack() },
                onStartWorkout = { type ->
                    // TODO: экран активной тренировки
                }
            )
        }

        // Модуль анализов
        composable(route = Screen.AnalysisDetail. route) {
            AnalysisScreen(
                onBackClick = { navController.popBackStack() },
                onAddClick = { /* TODO */ },
                onMetricClick = { type ->
                    // TODO:  экран истории показателя
                }
            )
        }
    }
}