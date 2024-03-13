package com.example.foodrecommenderapp.preference.data

import com.example.foodrecommenderapp.common.Resource
import com.example.foodrecommenderapp.common.constants.REPORT_COLLECTION
import com.example.foodrecommenderapp.preference.data.network.PreferenceGeneratorService
import com.example.foodrecommenderapp.preference.data.pojo.GenerateMeal
import com.example.foodrecommenderapp.preference.domain.PreferenceRepository
import com.example.foodrecommenderapp.report.model.Reports
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class DefaultPreferencesRepositoryImplementation @Inject constructor(
    private val repository: PreferenceGeneratorService,
    private val fireStoreDb: FirebaseFirestore
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

    override suspend fun saveReports(
        reports: Reports
    ): Flow<Resource<Unit>> = flow {
        emit(Resource.Loading())

        try {
            val docRef = fireStoreDb.collection(REPORT_COLLECTION).document(reports.date)

            fireStoreDb.runTransaction { transaction ->
                val snapshot = transaction.get(docRef)

                transaction.update(docRef, "totalSearches", FieldValue.increment(1))


                val currentPreferences =
                    snapshot.get("preferences") as? Map<String, Map<String, Int>> ?: emptyMap()

                reports.preferences.forEach { (category, preferenceMap) ->
                    preferenceMap.forEach { (preference, count) ->
                        val fieldPath = "preferences.$category.$preference"
                        transaction.update(docRef, fieldPath, FieldValue.increment(count.toLong()))
                    }

                }
            }.await()
            Resource.Success(Unit)
        } catch (e: Exception) {
            emit(Resource.Error(e.message ?: "An error occurred"))
        }
    }
}
/*
override suspend fun saveReports(
    date: String,
    category: String,
    preference: String
): Flow<Resource<Unit>> = flow {
    emit(Resource.Loading())

    try {
        val db = FirebaseFirestore.getInstance()

        // Get a reference to the document for the given date
        val docRef = db.collection(REPORT_COLLECTION).document(date)

        db.runTransaction { transaction ->
            val snapshot = transaction.get(docRef)

            // Increment the total number of searches
            var totalSearches = snapshot.getLong("totalSearches") ?: 0
            totalSearches++
            transaction.update(docRef, "totalSearches", totalSearches)

            // Get the current preferences map
            val currentPreferences = snapshot.get("preferences") as? Map<String, Map<String, Long>> ?: emptyMap()

            // Create a mutable copy of the current preferences map
            val updatedPreferences = currentPreferences.toMutableMap()

            // Get the current category map
            val currentCategory = updatedPreferences[category]?.toMutableMap() ?: mutableMapOf()

            // Get the current preference map within the category
            val currentPreference = currentCategory[preference]?.toMutableMap() ?: mutableMapOf()

            // Increment the count for this preference
            val currentCount = currentPreference[preference] ?: 0
            currentPreference[preference] = currentCount + 1

            // Update the preference map in the category map
            currentCategory[preference] = currentPreference

            // Update the category map in the preferences map
            updatedPreferences[category] = currentCategory

            // Update the preferences map in Firestore
            transaction.update(docRef, "preferences", updatedPreferences)
        }.addOnSuccessListener {
            emit(Resource.Success(Unit))
        }.addOnFailureListener { e ->
            emit(Resource.Error(e.message ?: "An error occurred"))
        }
    } catch (e: Exception) {
        emit(Resource.Error(e.message ?: "An error occurred"))
    }
}

*
 */