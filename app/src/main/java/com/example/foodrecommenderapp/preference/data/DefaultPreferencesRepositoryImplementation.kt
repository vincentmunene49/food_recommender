package com.example.foodrecommenderapp.preference.data

import com.example.foodrecommenderapp.common.Resource
import com.example.foodrecommenderapp.preference.data.network.PreferenceGeneratorService
import com.example.foodrecommenderapp.preference.data.pojo.GenerateMeal
import com.example.foodrecommenderapp.preference.domain.PreferenceRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class DefaultPreferencesRepositoryImplementation @Inject constructor(
    private val repository: PreferenceGeneratorService
) : PreferenceRepository {
    override suspend fun getMealByPreferences(
        health: List<String>,
        diet: List<String>,
        cuisineType: List<String>,
        mealType: List<String>,
        dishType: List<String>
    ): Flow<Resource<GenerateMeal>> = flow {

        emit(Resource.Loading())
        try {
            val response = repository.getFoodFromPreferences(
                health = health,
                diet = diet,
                cuisineType = cuisineType,
                mealType = mealType,
                dishType = dishType
            )
            emit(Resource.Success(response))
        } catch (e: Exception) {
            emit(Resource.Error(e.message ?: "An error occurred"))
        }

    }

}