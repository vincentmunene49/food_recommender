package com.example.foodrecommenderapp.auth.di

import com.example.foodrecommenderapp.auth.login.data.DefaultLoginRepositoryImplementation
import com.example.foodrecommenderapp.auth.login.domain.LoginRepository
import com.example.foodrecommenderapp.auth.register.data.DefaultRegisterRepositoryImplementation
import com.example.foodrecommenderapp.auth.register.domain.RegisterRepository
import com.example.foodrecommenderapp.common.data.DefaultFormValidatorRepositoryImplementation
import com.example.foodrecommenderapp.common.domain.FormValidatorRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
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
    ):LoginRepository = DefaultLoginRepositoryImplementation(firebaseAuth)

    @Provides
    @Singleton
    fun provideRegisterRepository(
        firebaseAuth: FirebaseAuth,
        firebaseFirestore: FirebaseFirestore
    ):RegisterRepository = DefaultRegisterRepositoryImplementation(firebaseAuth, firebaseFirestore)

    @Provides
    @Singleton
    fun provideFormValidatorRepository(): FormValidatorRepository = DefaultFormValidatorRepositoryImplementation()

}