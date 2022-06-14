package com.example.petshelter.di

import com.example.petshelter.navigation.AppNavigation
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NavigationModule {


    @Provides
    @Singleton
    fun provideNavigation(): AppNavigation = AppNavigation()
}