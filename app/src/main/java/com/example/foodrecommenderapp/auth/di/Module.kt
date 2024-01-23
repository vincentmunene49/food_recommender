package com.example.foodrecommenderapp.auth.di

import com.example.foodrecommenderapp.auth.login.data.DefaultLoginRepositoryImplementation
import com.example.foodrecommenderapp.auth.login.domain.LoginRepository
import com.example.foodrecommenderapp.auth.register.data.DefaultRegisterRepositoryImplementation
import com.example.foodrecommenderapp.auth.register.domain.RegisterRepository
import com.example.foodrecommenderapp.common.constants.BASE_URL
import com.example.foodrecommenderapp.common.data.DefaultFormValidatorRepositoryImplementation
import com.example.foodrecommenderapp.common.domain.FormValidatorRepository
import com.example.foodrecommenderapp.home.data.DefaultHomeRepositoryImplementation
import com.example.foodrecommenderapp.home.data.network.ApiService
import com.example.foodrecommenderapp.home.domain.HomeRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
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
    fun provideLoginRepository(
        firebaseAuth: FirebaseAuth
    ): LoginRepository = DefaultLoginRepositoryImplementation(firebaseAuth)

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

    fun provideRetrofit(gsonConverterFactory: GsonConverterFactory): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(gsonConverterFactory)
            .build()
    }

    @Provides
    @Singleton


    fun provideApiService(retrofit: Retrofit): ApiService {
        return retrofit.create(ApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideHomeRepository(
        apiService: ApiService
    ): HomeRepository = DefaultHomeRepositoryImplementation(apiService)
}