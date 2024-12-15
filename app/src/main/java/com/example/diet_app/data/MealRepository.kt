package com.example.diet_app.data

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class MealRepository {
    private val meals = mutableListOf<Meal>()

    fun getAllMeals(): Flow<List<Meal>> = flow {
        emit(meals)
    }

    suspend fun addMeal(meal: Meal) {
        meals.add(meal)
    }
}