package com.example.diet_app.models

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.diet_app.data.Meal
import com.example.diet_app.data.MealRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import java.time.LocalDate

class MealViewModel(private val repository: MealRepository) : ViewModel() {
    val meals = repository.getAllMeals()

    fun addMeal(meal: Meal) {
        viewModelScope.launch {
            repository.addMeal(meal)
        }
    }

    fun getAnalysisData(filter: String) = flow {
        val currentDate = LocalDate.now()
        val oneMonthAgo = currentDate.minusMonths(1)

        val filteredMeals = meals.first().filter { meal ->
            meal.date.isAfter(oneMonthAgo) &&
                    (filter == "All" || meal.type == filter)
        }

        val analysisData = AnalysisData(
            totalCalories = filteredMeals.sumOf { meal ->
                meal.foodItems.sumOf { it.calories }
            },
            totalCost = filteredMeals.sumOf { it.cost }.toInt(), // Double을 Int로 변환
            averageRating = filteredMeals.map { it.rating }.average().toFloat(),
            mostVisitedPlace = filteredMeals.groupBy { it.place }
                .maxByOrNull { it.value.size }?.key ?: ""
        )

        emit(analysisData)
    }.flowOn(Dispatchers.Default)
}

data class AnalysisData(
    val totalCalories: Int = 0,
    val totalCost: Int = 0,
    val averageRating: Float = 0f,
    val mostVisitedPlace: String = ""
)