package com.example.foodrecommenderapp.admin.menu.domain

import android.net.Uri
import com.example.foodrecommenderapp.admin.menu.model.Menu
import com.example.foodrecommenderapp.common.Resource
import kotlinx.coroutines.flow.Flow

interface MenuRepository {

    suspend fun addMenu(menu: Menu, imageUri:Uri):Flow<Resource<Menu>>


    suspend fun getAllMenus(): Flow<Resource<List<Menu>>>

}