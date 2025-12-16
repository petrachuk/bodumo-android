package com.bodumo.app.ui.components

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.bodumo.app.ui.navigation.BottomNavItem

/**
 * Компонент нижней навигационной панели.
 *
 * @param items - список элементов навигации
 * @param currentRoute - текущий активный маршрут
 * @param onItemClick - callback при нажатии на элемент
 */
@Composable
fun BottomNavigationBar(
    items: List<BottomNavItem>,
    currentRoute: String?,
    onItemClick: (BottomNavItem) -> Unit,
    modifier: Modifier = Modifier
) {
    NavigationBar(modifier = modifier) {
        items.forEach { item ->
            val isSelected = currentRoute == item. route

            NavigationBarItem(
                selected = isSelected,
                onClick = { onItemClick(item) },
                icon = {
                    Icon(
                        imageVector = if (isSelected) {
                            item.selectedIcon
                        } else {
                            item.unselectedIcon
                        },
                        contentDescription = item.title
                    )
                },
                label = {
                    Text(text = item.title)
                }
            )
        }
    }
}