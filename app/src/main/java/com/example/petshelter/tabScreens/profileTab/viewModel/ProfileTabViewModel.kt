package com.example.petshelter.tabScreens.profileTab.viewModel

import android.net.Uri
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.example.petshelter.domain.repository.localRepository.UserDataRepository
import com.example.petshelter.navigation.AppNavigation
import com.example.petshelter.navigation.routeObject.AppScreens
import com.example.petshelter.tabScreens.profileTab.model.ProfileTabUiState
import com.example.petshelter.tabScreens.profileTab.navigation.routeObject.ProfileScreenRoute
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ProfileTabViewModel @Inject constructor(
    val appNavigation: AppNavigation,
    val userDataRepository: UserDataRepository
) : ViewModel() {

    private val nameAndSurname = MutableLiveData("Имя")
    private val avatarUri = MutableLiveData<Uri?>(null)
    private val screenBusy = MutableLiveData(false)
    private val editProfileActive = MutableLiveData(false)
    private val errorMessage = MutableLiveData("")

    init {
        initialInfo()
    }
    fun initialInfo(){
    }

    val uiState = ProfileTabUiState(
        nameAndSurname = nameAndSurname,
        avatarUri = avatarUri,
        screenBusy = screenBusy,
        errorMessage = errorMessage
    )

    fun popBackStack(navController: NavController){
        navController.popBackStack()
    }

    fun navigateTo(navController: NavController, profileScreenRoute: ProfileScreenRoute){
        navController.navigate(profileScreenRoute.name) {
            navController.graph.startDestinationRoute?.let { route ->
                popUpTo(route) {
                    saveState = true
                }
            }
            launchSingleTop = true
            restoreState = true
        }
    }

    fun logoutCallback(){
        userDataRepository.clearLocalData()
        appNavigation.navigateTo(AppScreens.AuthSignUp)
    }

    fun addPhotoCallback(uri: Uri?) {
        avatarUri.postValue(uri)
    }
}