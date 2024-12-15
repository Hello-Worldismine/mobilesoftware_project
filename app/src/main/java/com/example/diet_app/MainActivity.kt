package com.example.diet_app

import DietAppTheme
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.example.diet_app.data.MealRepository
import com.example.diet_app.models.MealViewModel
import com.example.diet_app.models.MealViewModelFactory
import com.example.diet_app.navigation.SetupNavigation

class MainActivity : ComponentActivity() {
    private lateinit var repository: MealRepository // 초기화
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        repository = MealRepository() // 초기화
        setContent {
            DietAppTheme {
                val navController = rememberNavController()
                val viewModel: MealViewModel = viewModel(factory = MealViewModelFactory(repository))
                SetupNavigation(navController, viewModel)
            }
        }
    }
}