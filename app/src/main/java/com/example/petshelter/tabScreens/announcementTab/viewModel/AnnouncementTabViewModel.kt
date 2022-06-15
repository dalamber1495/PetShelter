package com.example.petshelter.tabScreens.announcementTab.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.petshelter.navigation.AppNavigation
import com.example.petshelter.tabScreens.announcementTab.common.AnimalsTabItem
import com.example.petshelter.tabScreens.announcementTab.model.AnnouncementTabUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AnnouncementTabViewModel @Inject constructor(
    val appNavigation: AppNavigation
): ViewModel() {

    private val animals = MutableLiveData(AnimalsTabItem.All.route)

    val uiState = AnnouncementTabUiState(
        animals = animals
    )

}