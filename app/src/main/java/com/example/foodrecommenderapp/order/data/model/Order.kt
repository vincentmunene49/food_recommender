package com.example.foodrecommenderapp.order.data.model

import com.example.foodrecommenderapp.admin.menu.model.Menu

data class Order(
    val id:String = "",
    val userId: String = "",
    val adminId: String = "",
    val userEmail: String = "",
    val menu: Menu = Menu(),
    val quantity: Int = 1
)
