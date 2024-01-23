package com.example.foodrecommenderapp.home.data

import com.example.foodrecommenderapp.common.Resource
import com.example.foodrecommenderapp.home.data.model.Meal
import com.example.foodrecommenderapp.home.data.network.ApiService
import com.example.foodrecommenderapp.home.domain.HomeRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class DefaultHomeRepositoryImplementation @Inject constructor(
    private val apiService: ApiService
) : HomeRepository {
    override suspend fun getRandomMeal(): Flow<Resource<Meal>> {
        return flow {
            emit(Resource.Loading())
            try {
                val response =
                    apiService.getRandomMeal()
                emit(Resource.Success(response))
            } catch (e: Exception) {
                emit(Resource.Error(e.message ?: "An error occurred"))
            }
        }
    }


    override suspend fun getMealByFirstLetter(searchTerm: String): Flow<Resource<Meal>> {

        return flow {
            emit(Resource.Loading())
            try {
                val response =
                    apiService.getMealsByFirstLetter(searchTerm)
                emit(Resource.Success(response))
            } catch (e: Exception) {
                emit(Resource.Error(e.message ?: "An error occurred"))
            }
        }
    }
}