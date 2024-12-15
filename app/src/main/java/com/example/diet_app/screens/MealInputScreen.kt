package com.example.diet_app.screens

import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.example.diet_app.data.FoodItem
import com.example.diet_app.data.Meal
import com.example.diet_app.models.MealViewModel
import com.example.diet_app.navigation.BottomNavigationBar
import java.time.LocalDate
import androidx.compose.runtime.remember
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.Composable
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.filled.AddAPhoto
import androidx.compose.material.icons.filled.Comment
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Place
import androidx.compose.material.icons.filled.Restaurant
import androidx.compose.material.icons.filled.Star
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.diet_app.ui.theme.JuaFont

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MealInputScreen(navController: NavHostController, viewModel: MealViewModel = viewModel()) {
    var date by remember { mutableStateOf(LocalDate.now()) }
    var mealType by remember { mutableStateOf("") }
    var place by remember { mutableStateOf("") }
    var imageUri by remember { mutableStateOf<Uri?>(null) }
    var foodItems by remember { mutableStateOf(listOf<FoodItem>()) }
    var cost by remember { mutableStateOf("") }
    var rating by remember { mutableStateOf(0) }
    var comment by remember { mutableStateOf("") }

    val context = LocalContext.current
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        imageUri = uri
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("식사 입력", color = MaterialTheme.colorScheme.onPrimary,
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
        LazyColumn(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .padding(16.dp)
        ) {
            item {
                SectionCard(
                    title = "날짜 선택",
                    icon = Icons.Default.DateRange
                ) {
                    DatePicker(date = date, onDateSelected = { date = it })
                }
            }

            item {
                SectionCard(
                    title = "식사 종류",
                    icon = Icons.Default.Restaurant
                ) {
                    MealTypeSelector(selectedType = mealType, onTypeSelected = { mealType = it })
                }
            }

            item {
                SectionCard(
                    title = "장소",
                    icon = Icons.Default.Place
                ) {
                    PlaceSelector(selectedPlace = place, onPlaceSelected = { place = it })
                }
            }

            item {
                SectionCard(
                    title = "사진 업로드",
                    icon = Icons.Default.AddAPhoto
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Box(
                            modifier = Modifier
                                .size(100.dp)
                                .border(1.dp, Color.Gray)
                                .background(Color(0xFFfffdfc))
                        ) {
                            if (imageUri != null) {
                                AsyncImage(
                                    model = imageUri,
                                    contentDescription = "Meal photo",
                                    modifier = Modifier.fillMaxSize()
                                )
                            } else {
                                Icon(
                                    imageVector = Icons.Default.CameraAlt,
                                    contentDescription = "Camera icon",
                                    modifier = Modifier
                                        .size(50.dp)
                                        .align(Alignment.Center)
                                )
                            }
                        }
                        Spacer(modifier = Modifier.width(16.dp))
                        Button(onClick = { launcher.launch("image/*") }) {
                            Text("사진 업로드(선택)")
                        }
                    }
                }
            }

            item {
                SectionCard(
                    title = "음식 정보 입력",
                    icon = Icons.Default.Restaurant
                ) {
                    FoodItemInput(onFoodItemAdded = { foodItems = foodItems + it })
                    Spacer(modifier = Modifier.height(8.dp))
                    foodItems.forEach { foodItem ->
                        Text("${foodItem.name}: ${foodItem.calories} kcal, ${foodItem.cost}원")
                    }
                }
            }

            item {
                SectionCard(
                    title = "평가",
                    icon = Icons.Default.Star
                ) {
                    Slider(
                        value = rating.toFloat(),
                        onValueChange = { rating = it.toInt() },
                        valueRange = 0f..5f,
                        steps = 4
                    )
                    Text("$rating / 5")
                }
            }

            item {
                SectionCard(
                    title = "한줄 평",
                    icon = Icons.Default.Comment
                ) {
                    OutlinedTextField(
                        value = comment,
                        onValueChange = { comment = it },
                        label = { Text("간단한 평을 입력해주세요.") },
                        modifier = Modifier.fillMaxWidth(),
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            containerColor = Color(0xFFfffdfc)
                        )
                    )
                }
            }

            item {
                Spacer(modifier = Modifier.height(16.dp))
                Button(
                    onClick = {
                        if (mealType.isBlank()) {
                            Toast.makeText(context, "식사 종류를 선택해주세요.", Toast.LENGTH_SHORT).show()
                        } else if (place.isBlank()) {
                            Toast.makeText(context, "장소를 선택해주세요.", Toast.LENGTH_SHORT).show()
                        }
                        //                        else if (foodItems.isEmpty()) {
//                            Toast.makeText(context, "음식을 추가해주세요.", Toast.LENGTH_SHORT).show()
//                        else if (cost.isBlank() || cost.toDoubleOrNull() == null) {
//                            Toast.makeText(context, "유효한 비용을 입력해주세요.", Toast.LENGTH_SHORT).show()
                        //}
//                        else if (rating == 0) {
//                            Toast.makeText(context, "평점을 선택해주세요.", Toast.LENGTH_SHORT).show()
                       // }
                            else {
                            val meal = Meal(
                                date = date,
                                type = mealType,
                                place = place,
                                imageUri = imageUri?.toString(),
                                foodItems = foodItems,
                                calories = foodItems.sumOf { it.calories },
                                cost = foodItems.sumOf { it.cost },
                                rating = rating,
                                comment = comment
                            )
                            viewModel.addMeal(meal)
                            navController.navigate("meal_list")
                        }
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("저장")
                }
            }
        }
    }
}

@Composable
fun SectionCard(
    title: String,
    icon: ImageVector,
    content: @Composable () -> Unit

) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        )
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(bottom = 8.dp)
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.primary
                )
            }
            content()
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FoodItemInput(onFoodItemAdded: (FoodItem) -> Unit) {
    var foodName by remember { mutableStateOf("") }
    var calories by remember { mutableStateOf("") }
    var cost by remember { mutableStateOf("") }

    Column {
        Row(verticalAlignment = Alignment.CenterVertically) {
            OutlinedTextField(
                value = foodName,
                onValueChange = { foodName = it },
                label = { Text("음식 이름") },
                modifier = Modifier.weight(1f),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    containerColor = Color(0xFFfffdfc)
                )
            )
            Spacer(modifier = Modifier.width(8.dp))
            OutlinedTextField(
                value = calories,
                onValueChange = { calories = it },
                label = { Text("칼로리(kcal)") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.weight(1f),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    containerColor = Color(0xFFfffdfc)
                )
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        Row(verticalAlignment = Alignment.CenterVertically) {
            OutlinedTextField(
                value = cost,
                onValueChange = { cost = it },
                label = { Text("가격") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.weight(1f),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    containerColor = Color(0xFFfffdfc)
                )
            )
            Spacer(modifier = Modifier.width(8.dp))
            Button(onClick = {
                if (foodName.isNotBlank() && calories.isNotBlank() && cost.isNotBlank()) {
                    onFoodItemAdded(
                        FoodItem(
                            foodName,
                            calories.toIntOrNull() ?: 0,
                            cost.toDoubleOrNull() ?: 0.0
                        )
                    )
                    foodName = ""
                    calories = ""
                    cost = ""
                }
            }) {
                Text("추가")
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatePicker(date: LocalDate, onDateSelected: (LocalDate) -> Unit) {
    var year by remember { mutableStateOf(date.year) }
    var month by remember { mutableStateOf(date.monthValue) }
    var day by remember { mutableStateOf(date.dayOfMonth) }

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        // Year Dropdown
        DateDropdown(
            value = year.toString(),
            onValueSelected = {
                year = it.toInt()
                onDateSelected(LocalDate.of(year, month, day))
            },
            values = (LocalDate.now().year downTo LocalDate.now().year - 10).map { it.toString() },
            modifier = Modifier.weight(0.4f)
        )

        // Month Dropdown
        DateDropdown(
            value = month.toString(),
            onValueSelected = {
                month = it.toInt()
                onDateSelected(LocalDate.of(year, month, day))
            },
            values = (1..12).map { it.toString() },
            modifier = Modifier.weight(0.3f)
        )

        // Day Dropdown
        DateDropdown(
            value = day.toString(),
            onValueSelected = {
                day = it.toInt()
                onDateSelected(LocalDate.of(year, month, day))
            },
            values = (1..31).map { it.toString() },
            modifier = Modifier.weight(0.3f)
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun DateDropdown(
    value: String,
    onValueSelected: (String) -> Unit,
    values: List<String>,
    modifier: Modifier = Modifier
) {
    var expanded by remember { mutableStateOf(false) }

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded },
        modifier = modifier
    ) {
        TextField(
            value = value,
            onValueChange = {},
            readOnly = true,
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
            modifier = Modifier.menuAnchor(),
            colors = TextFieldDefaults.colors(
                unfocusedContainerColor = Color(0xFFfffdfc),
                focusedContainerColor = Color(0xFFfffdfc)
            )
        )
        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            values.forEach { item ->
                DropdownMenuItem(
                    text = { Text(item) },
                    onClick = {
                        onValueSelected(item)
                        expanded = false
                    }
                )
            }
        }
    }
}


@Composable
fun MealTypeSelector(selectedType: String, onTypeSelected: (String) -> Unit) {
    val mealTypes = listOf("조식", "중식", "석식", "간식")
    Column {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            mealTypes.forEach { type ->
                OutlinedButton(
                    onClick = { onTypeSelected(type) },
                    colors = ButtonDefaults.outlinedButtonColors(
                        containerColor = Color(0xFFfffdfc),  // 흰색으로 변경
                        contentColor = if (type == selectedType)
                            MaterialTheme.colorScheme.primary
                        else MaterialTheme.colorScheme.onSurface
                    ),
                    border = BorderStroke(
                        1.dp,
                        if (type == selectedType)
                            MaterialTheme.colorScheme.primary
                        else MaterialTheme.colorScheme.onSurface.copy(alpha = 0.12f)
                    ),
                    modifier = Modifier
                        .weight(1f)
                        .padding(4.dp)
                ) {
                    Text(type)
                }
            }
        }
    }
}

@Composable
fun PlaceSelector(selectedPlace: String, onPlaceSelected: (String) -> Unit) {
    val places = listOf("상록원", "기숙사식당", "bhc", "가든쿡", "카페")
    Column {
        places.forEach { place ->
            OutlinedButton(
                onClick = { onPlaceSelected(place) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp),
                colors = ButtonDefaults.outlinedButtonColors(
                    containerColor = Color(0xFFfffdfc),  // 흰색으로 변경
                    contentColor = if (place == selectedPlace)
                        MaterialTheme.colorScheme.primary
                    else MaterialTheme.colorScheme.onSurface
                ),
                border = BorderStroke(
                    1.dp,
                    if (place == selectedPlace)
                        MaterialTheme.colorScheme.primary
                    else MaterialTheme.colorScheme.onSurface.copy(alpha = 0.12f)
                )
            ) {
                Text(place)
            }
        }
    }
}
