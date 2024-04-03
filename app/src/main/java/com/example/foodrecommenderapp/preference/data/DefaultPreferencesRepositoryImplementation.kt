package com.example.foodrecommenderapp.preference.data

import com.example.foodrecommenderapp.admin.menu.model.Menu
import com.example.foodrecommenderapp.common.Resource
import com.example.foodrecommenderapp.common.constants.REPORT_COLLECTION
import com.example.foodrecommenderapp.preference.data.network.PreferenceGeneratorService
import com.example.foodrecommenderapp.preference.data.pojo.GenerateMeal
import com.example.foodrecommenderapp.preference.domain.PreferenceRepository
import com.example.foodrecommenderapp.admin.report.model.Reports
import com.example.foodrecommenderapp.common.constants.MENU_COLLECTION
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import timber.log.Timber
import javax.inject.Inject
import kotlin.coroutines.cancellation.CancellationException

class DefaultPreferencesRepositoryImplementation @Inject constructor(
    private val fireStoreDb: FirebaseFirestore
) : PreferenceRepository {

    override suspend fun getMealByPreferences(
        health: List<String>?,
        diet: List<String>?,
        cuisineType: List<String>?,
        mealType: List<String>?,
        dishType: List<String>?
    ): Flow<Resource<List<Menu>>> = flow {

        emit(Resource.Loading())
        try {
            val responses = mutableListOf<List<Menu>>()

            if (!health.isNullOrEmpty()) {
                val healthResponse = fireStoreDb.collection(MENU_COLLECTION)
                    .whereArrayContainsAny("preferences.health", health)
                    .get().await()
                    .toObjects(Menu::class.java)
                responses.add(healthResponse)
            }

            if (!diet.isNullOrEmpty()) {
                val dietResponse = fireStoreDb.collection(MENU_COLLECTION)
                    .whereArrayContainsAny("preferences.diet", diet)
                    .get().await()
                    .toObjects(Menu::class.java)
                responses.add(dietResponse)
            }

            if (!cuisineType.isNullOrEmpty()) {
                val cuisineTypeResponse = fireStoreDb.collection(MENU_COLLECTION)
                    .whereArrayContainsAny("preferences.cousine", cuisineType)
                    .get().await()
                    .toObjects(Menu::class.java)
                responses.add(cuisineTypeResponse)
            }

            if (!mealType.isNullOrEmpty()) {
                val mealTypeResponse = fireStoreDb.collection(MENU_COLLECTION)
                    .whereArrayContainsAny("preferences.mealType", mealType)
                    .get().await()
                    .toObjects(Menu::class.java)
                responses.add(mealTypeResponse)
            }

            if (!dishType.isNullOrEmpty()) {
                val dishTypeResponse = fireStoreDb.collection(MENU_COLLECTION)
                    .whereArrayContainsAny("preferences.dishType", dishType)
                    .get().await()
                    .toObjects(Menu::class.java)
                responses.add(dishTypeResponse)
            }

            val response = responses.flatten().distinctBy { it.id }

            if(response.isEmpty()){
                emit(Resource.Success(emptyList()))
            }else {
                emit(Resource.Success(response))
            }
        } catch (e: Exception) {
            emit(Resource.Error(e.message ?: "An error occurred"))
        }
    }
    override suspend fun getAllMenus(): Flow<Resource<List<Menu>>> = flow {
        emit(Resource.Loading())
        try {
            val result = fireStoreDb.collection(MENU_COLLECTION).get().await()
            val menu = result.toObjects(Menu::class.java)
            emit(Resource.Success(menu))
        } catch (e: Exception) {
            if (e is CancellationException) {
                throw e
            }
            emit(Resource.Error("☹\uFE0F \n ${e.message}"))
        }
    }

    override suspend fun saveReports(
        reports: Reports
    ): Flow<Resource<Unit>> = flow {
        emit(Resource.Loading())

        try {
            val docRef = fireStoreDb.collection(REPORT_COLLECTION).document(reports.date)

            fireStoreDb.runTransaction { transaction ->
                val data = mutableMapOf<String, Any>()
                data["totalSearches"] = FieldValue.increment(1)

                val preferencesData = mutableMapOf<String, Any>()
                reports.preferences.forEach { (category, preferenceMap) ->
                    val categoryData = mutableMapOf<String, Any>()
                    preferenceMap.forEach { (preference, count) ->
                        categoryData[preference] = FieldValue.increment(count.toLong())
                    }
                    preferencesData[category] = categoryData
                }
                data["preferences"] = preferencesData

                transaction.set(docRef, data, SetOptions.merge())
            }.await()
            emit(Resource.Success(Unit))
        } catch (e: Exception) {
            emit(Resource.Error(e.message ?: "An error occurred"))
        }
    }
}
