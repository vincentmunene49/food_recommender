package com.example.foodrecommenderapp.report.model

data class Reports(
    var date: String = "",
    var totalSearches: Long = 0,
    var preferences: Map<String, Map<String, Int>> = emptyMap()
)
