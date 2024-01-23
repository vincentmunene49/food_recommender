package com.example.foodrecommenderapp.home.data.model

data class Preferences(
    val diet:List<String> = listOf("High Protein", "Low Carb", "Low Fat", "Balanced"),
    val allergies:List<String> = listOf("Dairy", "Egg", "Gluten", "Grain", "Peanut", "Seafood", "Sesame", "Shellfish", "Soy", "Sulfite", "Tree Nut", "Wheat"),
    val cuisines:List<String> = listOf("American", "British", "Chinese", "French", "Greek", "Indian", "Irish", "Italian", "Japanese", "Korean", "Mexican", "Middle Eastern", "Nordic", "Southern", "Spanish", "Thai", "Vietnamese"),
    val mealType:List<String> = listOf("Breakfast", "Lunch", "Dinner", "Snack", "Teatime"),
    val calories: List<Int> = listOf(100, 200, 300, 400, 500, 600, 700, 800, 900, 1000),
) {
}