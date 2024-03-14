package com.example.foodrecommenderapp.admin.common.domain

import android.net.Uri
import com.example.foodrecommenderapp.admin.menu.model.Menu
import com.example.foodrecommenderapp.common.Resource
import com.example.foodrecommenderapp.admin.report.model.Reports
import kotlinx.coroutines.flow.Flow

interface AdminRepository {

    suspend fun addMenu(menu: Menu, imageUri:Uri):Flow<Resource<Menu>>

    suspend fun getReports(date:String): Flow<Resource<Reports?>>

}