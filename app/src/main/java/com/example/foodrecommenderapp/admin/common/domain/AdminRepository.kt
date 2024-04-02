package com.example.foodrecommenderapp.admin.common.domain

import android.net.Uri
import com.example.foodrecommenderapp.admin.menu.model.Category
import com.example.foodrecommenderapp.admin.menu.model.Menu
import com.example.foodrecommenderapp.common.Resource
import com.example.foodrecommenderapp.admin.report.model.Reports
import com.example.foodrecommenderapp.order.data.model.Order
import kotlinx.coroutines.flow.Flow

interface AdminRepository {

    suspend fun addMenu(menu: Menu, foodImage:ByteArray, category: Category, categoryImage:ByteArray):Flow<Resource<Menu>>

    suspend fun getReports(date:String): Flow<Resource<Reports?>>

    suspend fun getOrderReport(): Flow<Resource<List<Order>>>

    suspend fun deleteOrder(order: Order): Flow<Resource<Order>>

    suspend fun logOut(): Flow<Resource<Boolean>>

}