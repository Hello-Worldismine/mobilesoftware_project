package com.example.diet_app.data

import android.net.Uri
import java.time.LocalDate
import java.util.UUID

data class Meal(
    val id: String = UUID.randomUUID().toString(),
    val date: LocalDate,
    val type: String,
    val place: String,
    val foodItems: List<FoodItem>,
    val calories: Int,
    val cost : Double,
    val rating: Int,
    val comment: String,
    val imageUri: String?
) {
    val totalCalories: Int
        get() = foodItems.sumOf { it.calories }

    val totalCost: Double
        get() = foodItems.sumOf { it.cost }
}

data class FoodItem(
    val name: String,
    val calories: Int,
    val cost: Double
)