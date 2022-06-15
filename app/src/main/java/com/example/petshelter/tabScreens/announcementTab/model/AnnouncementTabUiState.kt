package com.example.petshelter.tabScreens.announcementTab.model

import androidx.lifecycle.LiveData

data class AnnouncementTabUiState(
    val animals:LiveData<String>
)
