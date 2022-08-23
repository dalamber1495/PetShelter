package com.example.petshelter.tabScreens.announcementTab.model

import androidx.lifecycle.LiveData
import com.example.petshelter.domain.model.AnnouncementsListState

data class AnnouncementTabUiState(
    val animalsTabs:LiveData<String>,
    val animalsState: LiveData<AnnouncementsListState>,
    val listDogs:LiveData<AnnouncementsListState>,
    val listCats:LiveData<AnnouncementsListState>,
    val listOther:LiveData<AnnouncementsListState>,
    val isRefreshing:LiveData<Boolean>
)
