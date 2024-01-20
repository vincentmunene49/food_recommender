package com.example.foodrecommenderapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.example.foodrecommenderapp.auth.register.presentation.RegisterScreen
import com.example.foodrecommenderapp.auth.register.presentation.RegisterViewModel
import com.example.foodrecommenderapp.navigation.NavGraph
import com.example.foodrecommenderapp.ui.theme.FoodRecommenderAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            FoodRecommenderAppTheme {
                NavGraph()
            }
        }
    }
}



@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    FoodRecommenderAppTheme {

    }
}