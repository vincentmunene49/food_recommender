package com.example.foodrecommenderapp.home.data

import com.example.foodrecommenderapp.admin.menu.model.Category
import com.example.foodrecommenderapp.admin.menu.model.Menu
import com.example.foodrecommenderapp.common.Resource
import com.example.foodrecommenderapp.common.constants.CATEGORY_COLLECTION
import com.example.foodrecommenderapp.common.constants.MENU_COLLECTION
import com.example.foodrecommenderapp.home.domain.HomeRepository
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class DefaultHomeRepositoryImplementation @Inject constructor(
    private val firebaseFirestore: FirebaseFirestore
) : HomeRepository {


    override suspend fun searchMeal(searchTerm: String): Flow<Resource<List<Menu>>> {

        return flow {
            emit(Resource.Loading())
            try {
                val result =
                    firebaseFirestore.collection(MENU_COLLECTION).whereEqualTo("name", searchTerm)
                        .get().await()
                val meals = result.toObjects(Menu::class.java)
                if (meals.isNotEmpty()) {
                    emit(Resource.Success(meals))

                } else {
                    emit(Resource.Success(emptyList()))
                }
            } catch (e: Exception) {
                emit(Resource.Error(e.message ?: "An error occurred"))
            }
        }
    }

    override suspend fun getMealCategories(): Flow<Resource<List<Category>>> {
        return flow {
            emit(Resource.Loading())
            try {
                val result = firebaseFirestore.collection(CATEGORY_COLLECTION).get().await()
                val categories = result.toObjects(Category::class.java)
                if (categories.isNotEmpty()) {
                    emit(Resource.Success(categories))
                } else {
                    emit(Resource.Success(emptyList()))
                }
            } catch (e: Exception) {
                emit(Resource.Error(e.message ?: "An error occurred"))
            }
        }
    }

    override suspend fun getMeals(): Flow<Resource<List<Menu>>> {
        return flow {
            emit(Resource.Loading())
            try {
                val result = firebaseFirestore.collection(MENU_COLLECTION).get().await()
                val meals = result.toObjects(Menu::class.java)
                if (meals.isNotEmpty()) {
                    emit(Resource.Success(meals))
                } else {
                    emit(Resource.Success(emptyList()))
                }
            } catch (e: Exception) {
                emit(Resource.Error(e.message ?: "An error occurred"))
            }
        }
    }

    override suspend fun getMealsByCategory(category: String): Flow<Resource<List<Menu>>> {
        return flow {
            emit(Resource.Loading())
            try {
                val result = firebaseFirestore.collection(MENU_COLLECTION).whereEqualTo("category", category)
                    .get().await()
                val meals = result.toObjects(Menu::class.java)
                if (meals.isNotEmpty()) {
                    emit(Resource.Success(meals))
                } else {
                    emit(Resource.Success(emptyList()))
                }
            } catch (e: Exception) {
                emit(Resource.Error(e.message ?: "An error occurred"))
            }
        }
    }
}