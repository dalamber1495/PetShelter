package com.example.petshelter.di

import android.content.Context
import com.example.petshelter.common.Constants.BASE_URL
import com.example.petshelter.data.remote.PetShelterApi
import com.example.petshelter.domain.repository.backendAnimals.AnnouncementRepositoryImpl
import com.example.petshelter.domain.repository.backendAnimals.AnnouncementsRepository
import com.example.petshelter.domain.repository.firebase.FirebaseBackend
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun providePetShelterApi():PetShelterApi{
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(PetShelterApi::class.java)
    }

    @Provides
    @Singleton
    fun provideAnnouncementRepository(api:PetShelterApi): AnnouncementsRepository {
        return AnnouncementRepositoryImpl(api)
    }
    @Provides
    @Singleton
    fun provideFirebaseBackend(@ApplicationContext context: Context): FirebaseBackend =
        FirebaseBackend()
}