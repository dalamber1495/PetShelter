package com.example.petshelter.tabScreens.profileTab.viewModel

import androidx.lifecycle.ViewModel
import com.example.petshelter.navigation.AppNavigation
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ProfileTabViewModel @Inject constructor(
    val appNavigation: AppNavigation
): ViewModel(){
}