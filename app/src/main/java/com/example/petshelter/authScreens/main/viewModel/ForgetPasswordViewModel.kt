package com.example.petshelter.authScreens.main.viewModel

import androidx.lifecycle.ViewModel
import com.example.petshelter.navigation.AppNavigation
import com.example.petshelter.navigation.routeObject.AppScreens
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ForgetPasswordViewModel @Inject constructor(
    val appNavigation: AppNavigation
):ViewModel() {


    fun backToAuthScreen(){
        appNavigation.navigateTo(AppScreens.PopBackStack)
    }
}