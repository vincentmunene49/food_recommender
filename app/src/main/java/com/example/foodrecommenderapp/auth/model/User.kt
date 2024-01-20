package com.example.foodrecommenderapp.auth.model

data class User(
    val userName: String = "",
    val email: String = "",
    val imagePath: String = ""
) {
    constructor() : this(userName ="", imagePath = "")
}