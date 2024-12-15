package com.example.diet_app.navigation

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.diet_app.screens.MealInputScreen
import com.example.diet_app.screens.SplashScreen
import com.example.diet_app.screens.HomeScreen
import com.example.diet_app.screens.MealListScreen
import com.example.diet_app.screens.MealAnalysisScreen
import androidx.compose.material3.*
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.example.diet_app.models.MealViewModel
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip

@Composable
fun SetupNavigation(navController: NavHostController, viewModel: MealViewModel) {
    NavHost(navController = navController, startDestination = "splash") {
        composable("splash") { SplashScreen(navController) }
        composable("home") { HomeScreen(navController, viewModel) }
        composable("meal_input") { MealInputScreen(navController, viewModel) }
        composable("meal_list") { MealListScreen(navController, viewModel) }
        composable("meal_analysis") { MealAnalysisScreen(navController, viewModel) }
    }
}

@Composable
fun BottomNavigationBar(navController: NavHostController) {
    NavigationBar(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp)), // 상단 모서리를 둥글게 설정
        containerColor = MaterialTheme.colorScheme.surface, // 네비게이션 바 배경색
    ) {
        val items = listOf(
            NavigationItem("Home", "home", Icons.Default.Home),
            NavigationItem("Input", "meal_input", Icons.Default.Add),
            NavigationItem("List", "meal_list", Icons.Default.List),
            NavigationItem("Analysis", "meal_analysis", Icons.Default.PieChart)
        )

        val navBackStackEntry = navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry.value?.destination?.route

        items.forEach { item ->
            NavigationBarItem(
                icon = { Icon(imageVector = item.icon, contentDescription = item.label) },
                label = { Text(item.label) },
                selected = currentRoute == item.route,
                onClick = {
                    navController.navigate(item.route) {
                        popUpTo(navController.graph.startDestinationId) { inclusive = true }
                        launchSingleTop = true
                    }
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = MaterialTheme.colorScheme.onPrimary,
                    selectedTextColor = MaterialTheme.colorScheme.onPrimary,
                    indicatorColor = MaterialTheme.colorScheme.primary, // 선택된 아이템의 배경색
                    unselectedIconColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
                    unselectedTextColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                )
            )
        }
    }
}


data class NavigationItem(val label: String, val route: String, val icon: ImageVector)