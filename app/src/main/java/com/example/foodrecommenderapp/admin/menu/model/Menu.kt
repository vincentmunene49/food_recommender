package com.example.foodrecommenderapp.admin.menu.model

import android.net.Uri


data class Menu(
    val name: String = "",
    val category: String = "",
    val image: Uri? = null,
    val ingredients: List<String> = emptyList(),
    val preferences: Map<String, List<String>> = emptyMap()
) {
}
