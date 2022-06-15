package com.example.petshelter.tabScreens.mainScreen.model

import androidx.lifecycle.LiveData

data class MainScreenUIState(
    val currentTabRoute: LiveData<String>
)