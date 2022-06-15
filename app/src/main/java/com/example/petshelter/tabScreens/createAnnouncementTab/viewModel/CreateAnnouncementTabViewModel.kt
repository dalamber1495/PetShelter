package com.example.petshelter.tabScreens.createAnnouncementTab.viewModel

import androidx.lifecycle.ViewModel
import com.example.petshelter.navigation.AppNavigation
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CreateAnnouncementTabViewModel @Inject constructor(
    val appNavigation: AppNavigation
):ViewModel(){
}