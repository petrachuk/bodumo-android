package com.bodumo.app.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.BarChart
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.BarChart
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.ui.graphics.vector.ImageVector

/**
 * Описание элемента Bottom Navigation Bar.
 *
 * @param route - маршрут для навигации
 * @param title - название вкладки
 * @param selectedIcon - иконка когда вкладка активна
 * @param unselectedIcon - иконка когда вкладка неактивна
 */
data class BottomNavItem(
    val route: String,
    val title: String,
    val selectedIcon: ImageVector,
    val unselectedIcon:  ImageVector
)

/**
 * Список всех вкладок Bottom Navigation.
 */
val bottomNavItems = listOf(
    BottomNavItem(
        route = Screen.Home.route,
        title = "Главная",
        selectedIcon = Icons. Filled.Home,
        unselectedIcon = Icons.Outlined. Home
    ),
    BottomNavItem(
        route = Screen.Statistics.route,
        title = "Статистика",
        selectedIcon = Icons. Filled.BarChart,
        unselectedIcon = Icons. Outlined.BarChart
    ),
    BottomNavItem(
        route = Screen.QuickAction.route,
        title = "Добавить",
        selectedIcon = Icons.Filled.Add,
        unselectedIcon = Icons. Outlined.Add
    ),
    BottomNavItem(
        route = Screen.Profile.route,
        title = "Профиль",
        selectedIcon = Icons.Filled.Person,
        unselectedIcon = Icons. Outlined.Person
    ),
    BottomNavItem(
        route = Screen.Settings.route,
        title = "Настройки",
        selectedIcon = Icons.Filled.Settings,
        unselectedIcon = Icons. Outlined.Settings
    )
)