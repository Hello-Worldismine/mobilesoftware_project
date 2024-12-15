package com.example.diet_app.screens

import android.widget.Space
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.example.diet_app.data.Meal
import com.example.diet_app.models.MealViewModel
import com.example.diet_app.navigation.BottomNavigationBar
import com.example.diet_app.ui.theme.JuaFont

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MealListScreen(navController: NavHostController, viewModel: MealViewModel = androidx.lifecycle.viewmodel.compose.viewModel()) {
    val meals by viewModel.meals.collectAsState(initial = emptyList())
    var selectedMeal by remember { mutableStateOf<Meal?>(null) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("식사 리스트", color = MaterialTheme.colorScheme.onPrimary,
                    style = MaterialTheme.typography.headlineMedium.copy(
                        fontFamily = JuaFont
                    )) },
                colors = TopAppBarDefaults.smallTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary
                )
            )
        },
        bottomBar = { BottomNavigationBar(navController) }
    ) { padding ->
        Box(modifier = Modifier.fillMaxSize().padding(padding)) {
            if (meals.isEmpty()) {

                Text(
                    text = "생성된 식사 리스트가 없습니다",
                    modifier = Modifier.align(Alignment.Center),
                    style = MaterialTheme.typography.bodyLarge
                )
            } else {
                LazyColumn {
                    items(meals) { meal ->
                        MealItem(meal = meal, onItemClick = { selectedMeal = meal })
                    }
                }
            }

            selectedMeal?.let { meal ->
                MealDetailDialog(meal = meal, onDismiss = { selectedMeal = null })
            }
        }
    }
}


@Composable
fun MealItem(meal: Meal, onItemClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable(onClick = onItemClick),
                colors = CardDefaults.cardColors(
                containerColor = Color(0xFFfffdfc)
                )
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            meal.imageUri?.let { uri ->
                AsyncImage(
                    model = uri,
                    contentDescription = "Meal photo",
                    modifier = Modifier.size(60.dp)
                )
                Spacer(modifier = Modifier.width(16.dp))
            }
            Column {
                Text(text = "날짜: ${meal.date}", style = MaterialTheme.typography.bodyLarge)
                Text(text = "식사 종류: ${meal.type}", style = MaterialTheme.typography.bodyMedium)
                Text(text = "점수: ${meal.rating}/5", style = MaterialTheme.typography.bodyMedium)
            }
        }
    }
}

@Composable
fun MealDetailDialog(meal: Meal, onDismiss: () -> Unit) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("식사 세부 정보") },
        text = {
            Column {
                Text("날짜: ${meal.date}")
                Text("식사 유형: ${meal.type}")
                Text("식사 장소: ${meal.place}")
                Text("[음식 정보]")
                meal.foodItems.forEach { foodItem ->
                    Text("- ${foodItem.name}: ${foodItem.calories} kcal, Cost: ${foodItem.cost.toInt()}원")
                }
                Text("총 칼로리(kcal): ${meal.totalCalories} kcal")
                Text("총 비용(won): ${meal.totalCost.toInt()} 원")
                Text("점수: ${meal.rating}/5")
                Text("한줄 평: ${meal.comment}")
            }
        },
        confirmButton = {
            TextButton(onClick = onDismiss) {
                Text("Close")
            }
        }
    )
}
