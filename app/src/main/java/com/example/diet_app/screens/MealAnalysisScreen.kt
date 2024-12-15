package com.example.diet_app.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Analytics
import androidx.compose.material.icons.filled.AttachMoney
import androidx.compose.material.icons.filled.LocalDining
import androidx.compose.material.icons.filled.Place
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.diet_app.models.AnalysisData
import com.example.diet_app.models.MealViewModel
import com.example.diet_app.navigation.BottomNavigationBar
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.colorResource
import com.example.diet_app.ui.theme.JuaFont

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MealAnalysisScreen(navController: NavHostController, viewModel: MealViewModel = androidx.lifecycle.viewmodel.compose.viewModel()) {
    var selectedFilter by remember { mutableStateOf("All") }
    val analysisData by viewModel.getAnalysisData(selectedFilter).collectAsState(initial = AnalysisData())

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("식단 분석", color = MaterialTheme.colorScheme.onPrimary,
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
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
                .fillMaxSize()
        ) {
            FilterSelector(selectedFilter = selectedFilter, onFilterSelected = { selectedFilter = it })
            Spacer(modifier = Modifier.height(24.dp))

            Card(
                modifier = Modifier.fillMaxWidth(),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surface
                )
            ) {
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.Analytics,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.primary
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            "최근 한 달 간의 식단 분석:",
                            style = MaterialTheme.typography.headlineSmall
                        )
                    }
                    Spacer(modifier = Modifier.height(16.dp))

                    if (analysisData.totalCalories == 0 && analysisData.totalCost == 0 && analysisData.mostVisitedPlace.isEmpty()) {
                        Text(
                            "최근 한 달 간의 식단 내역이 존재하지 않습니다",
                            style = MaterialTheme.typography.bodyLarge,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    } else {
                        AnalysisItem(
                            icon = Icons.Default.LocalDining,
                            label = "섭취한 총 칼로리",
                            value = "${analysisData.totalCalories} kcal"
                        )
                        AnalysisItem(
                            icon = Icons.Default.AttachMoney,
                            label = "총 비용",
                            value = "${analysisData.totalCost} 원"
                        )
                        AnalysisItem(
                            icon = Icons.Default.Star,
                            label = "평점",
                            value = "${analysisData.averageRating}/5"
                        )
                        AnalysisItem(
                            icon = Icons.Default.Place,
                            label = "가장 많이 방문한 장소",
                            value = analysisData.mostVisitedPlace
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun AnalysisItem(
    icon: ImageVector,
    label: String,
    value: String
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.primary,
            modifier = Modifier.size(24.dp)
        )
        Spacer(modifier = Modifier.width(16.dp))
        Column {
            Text(
                text = label,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Text(
                text = value,
                style = MaterialTheme.typography.titleMedium
            )
        }
    }
}



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FilterSelector(selectedFilter: String, onFilterSelected: (String) -> Unit) {
    val filters = listOf("All", "조식", "중식", "석식", "간식")
    var expanded by remember { mutableStateOf(false) }

    Column {
        Text("분류:", style = MaterialTheme.typography.bodyLarge)
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surface
            )
        ) {
            ExposedDropdownMenuBox(
                expanded = expanded,
                onExpandedChange = { expanded = !expanded }
            ) {
                TextField(
                    value = selectedFilter,
                    onValueChange = {},
                    readOnly = true,
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                    modifier = Modifier
                        .menuAnchor()
                        .fillMaxWidth(),
                    colors = TextFieldDefaults.colors(
                        unfocusedContainerColor = Color.Transparent,
                        focusedContainerColor = Color.Transparent
                    )
                )
                ExposedDropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false },
                    modifier = Modifier.background(MaterialTheme.colorScheme.surface)
                ) {
                    filters.forEach { filter ->
                        DropdownMenuItem(
                            text = { Text(filter) },
                            onClick = {
                                onFilterSelected(filter)
                                expanded = false
                            },
                            colors = MenuDefaults.itemColors(
                                textColor = MaterialTheme.colorScheme.onSurface
                            )
                        )
                    }
                }
            }
        }
    }
}
