package com.example.foodrecommenderapp.auth.di

import com.example.foodrecommenderapp.auth.login.data.DefaultLoginRepositoryImplementation
import com.example.foodrecommenderapp.auth.login.domain.LoginRepository
import com.example.foodrecommenderapp.auth.register.data.DefaultRegisterRepositoryImplementation
import com.example.foodrecommenderapp.auth.register.domain.RegisterRepository
import com.example.foodrecommenderapp.common.constants.BASE_URL
import com.example.foodrecommenderapp.common.constants.PREFERENCES_BASE_URL
import com.example.foodrecommenderapp.common.data.DefaultFormValidatorRepositoryImplementation
import com.example.foodrecommenderapp.common.domain.FormValidatorRepository
import com.example.foodrecommenderapp.home.data.DefaultHomeRepositoryImplementation
import com.example.foodrecommenderapp.preference.data.DefaultPreferencesRepositoryImplementation
import com.example.foodrecommenderapp.home.data.network.ApiService
import com.example.foodrecommenderapp.preference.data.network.PreferenceGeneratorService
import com.example.foodrecommenderapp.home.domain.HomeRepository
import com.example.foodrecommenderapp.preference.domain.PreferenceRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object Module {

    @Provides
    @Singleton
    fun provideFirebaseAuth() = FirebaseAuth.getInstance()

    @Provides
    @Singleton
    fun provideFirebaseStore() = Firebase.firestore

    @Provides
    @Singleton
    fun provideFirebaseStorage(): FirebaseStorage = FirebaseStorage.getInstance()
    @Provides
    @Singleton
    fun provideLoginRepository(
        firebaseAuth: FirebaseAuth,
        firebaseFirestore: FirebaseFirestore
    ): LoginRepository = DefaultLoginRepositoryImplementation(firebaseAuth, firebaseFirestore)

    @Provides
    @Singleton
    fun provideRegisterRepository(
        firebaseAuth: FirebaseAuth,
        firebaseFirestore: FirebaseFirestore
    ): RegisterRepository = DefaultRegisterRepositoryImplementation(firebaseAuth, firebaseFirestore)

    @Provides
    @Singleton
    fun provideFormValidatorRepository(): FormValidatorRepository =
        DefaultFormValidatorRepositoryImplementation()

    @Provides
    @Singleton

    fun provideGsonConverterFactory(): GsonConverterFactory {
        return GsonConverterFactory.create()
    }

    @Provides
    @Singleton
    @Named("DefaultRetrofit")
    fun provideRetrofit(gsonConverterFactory: GsonConverterFactory): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(gsonConverterFactory)
            .build()
    }

    @Provides
    @Singleton
    @Named("PreferenceGeneratorRetrofit")
    fun providePreferenceGeneratorRetrofit(gsonConverterFactory: GsonConverterFactory): Retrofit {
        return Retrofit.Builder()
            .baseUrl(PREFERENCES_BASE_URL)
            .addConverterFactory(gsonConverterFactory)
            .build()
    }

    @Provides
    @Singleton

    fun provideApiService(@Named("DefaultRetrofit") retrofit: Retrofit): ApiService {
        return retrofit.create(ApiService::class.java)
    }

    @Provides
    @Singleton
    fun providePreferenceGeneratorService(
        @Named("PreferenceGeneratorRetrofit")  retrofit: Retrofit
    ): PreferenceGeneratorService {
        return retrofit.create(PreferenceGeneratorService::class.java)
    }

    @Provides
    @Singleton
    fun provideHomeRepository(
        apiService: ApiService
    ): HomeRepository = DefaultHomeRepositoryImplementation(apiService)

    @Provides
    @Singleton
    fun providePreferenceRepository(preferenceGeneratorService: PreferenceGeneratorService): PreferenceRepository =
        DefaultPreferencesRepositoryImplementation(preferenceGeneratorService)
}