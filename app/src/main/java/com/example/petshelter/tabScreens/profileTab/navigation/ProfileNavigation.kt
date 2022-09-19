package com.example.petshelter.tabScreens.profileTab.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.petshelter.tabScreens.profileTab.navigation.routeObject.ProfileScreenRoute
import com.example.petshelter.tabScreens.profileTab.view.ChangePasswordScreen
import com.example.petshelter.tabScreens.profileTab.view.EditProfileScreen
import com.example.petshelter.tabScreens.profileTab.view.ProfileTabScreen
import com.example.petshelter.tabScreens.profileTab.viewModel.ProfileTabViewModel


@Composable
fun ProfileNavigation(){
    val viewModel = hiltViewModel<ProfileTabViewModel>()
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = ProfileScreenRoute.ProfileScreen.name){
        composable(ProfileScreenRoute.ProfileScreen.name){
            ProfileTabScreen(
                viewModel::addPhotoCallback,
                viewModel.uiState,
                navController,
                viewModel::logoutCallback,
                viewModel::navigateTo
            )
        }
        composable(ProfileScreenRoute.EditProfile.name){
            EditProfileScreen(
                addPhotoCallback = viewModel::addPhotoCallback,
                uiState = viewModel.uiState,
                popBackStackCallback = viewModel::popBackStack,
                navController = navController,
            )
        }
        composable(ProfileScreenRoute.ChangePassword.name){
            ChangePasswordScreen(
                addPhotoCallback = viewModel::addPhotoCallback,
                uiState = viewModel.uiState,
                popBackStackCallback = viewModel::popBackStack,
                navController = navController
            )
        }
    }

}