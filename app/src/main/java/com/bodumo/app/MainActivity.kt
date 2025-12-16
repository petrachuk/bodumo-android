package com.bodumo.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.bodumo.app.ui.components.BottomNavigationBar
import com.bodumo.app.ui.navigation.AppNavHost
import com.bodumo.app.ui.navigation.bottomNavItems
import com.bodumo.app.ui.theme.BodumoTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            BodumoTheme {
                MainApp()
            }
        }
    }
}

@Composable
fun MainApp() {
    // Создаём NavController для управления навигацией
    val navController = rememberNavController()

    // Получаем текущий маршрут для подсветки активной вкладки
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            BottomNavigationBar(
                items = bottomNavItems,
                currentRoute = currentRoute,
                onItemClick = { item ->
                    navController.navigate(item.route) {
                        // Избегаем накопления экранов в back stack
                        popUpTo(navController.graph. findStartDestination().id) {
                            saveState = true
                        }
                        // Избегаем дублирования экранов
                        launchSingleTop = true
                        // Восстанавливаем состояние при возврате
                        restoreState = true
                    }
                }
            )
        }
    ) { innerPadding ->
        AppNavHost(
            navController = navController,
            modifier = Modifier.padding(innerPadding)
        )
    }
}