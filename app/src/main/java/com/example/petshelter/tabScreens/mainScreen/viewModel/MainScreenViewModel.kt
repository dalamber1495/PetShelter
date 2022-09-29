package com.example.petshelter.tabScreens.mainScreen.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.petshelter.domain.repository.localRepository.UserDataRepository
import com.example.petshelter.navigation.AppNavigation
import com.example.petshelter.tabScreens.mainScreen.components.BottomBarItem
import com.example.petshelter.tabScreens.mainScreen.model.MainScreenUIState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainScreenViewModel @Inject constructor(
    private val appNavigation: AppNavigation,
    private val userDataRepository: UserDataRepository
): ViewModel() {

    private val currentTabRoute = MutableLiveData(BottomBarItem.AnnouncementTabItem.route)

    val uiState = MainScreenUIState(
        currentTabRoute
    )
    fun authState() =
        userDataRepository.isUserLoggedIn()

    fun setCurrentTabRoute(tabRoute:String){
        currentTabRoute.postValue(tabRoute)
    }
}