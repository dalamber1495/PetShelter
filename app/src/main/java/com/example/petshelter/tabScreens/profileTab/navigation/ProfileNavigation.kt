package com.example.petshelter.tabScreens.profileTab.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.petshelter.authScreens.main.components.AuthTabScreen
import com.example.petshelter.authScreens.main.viewModel.AuthViewModel
import com.example.petshelter.tabScreens.profileTab.navigation.routeObject.ProfileScreenRoute
import com.example.petshelter.tabScreens.profileTab.view.ChangePasswordScreen
import com.example.petshelter.tabScreens.profileTab.view.EditProfileScreen
import com.example.petshelter.tabScreens.profileTab.view.ProfileTabScreen
import com.example.petshelter.tabScreens.profileTab.viewModel.ProfileTabViewModel
import com.google.accompanist.pager.ExperimentalPagerApi


@Composable
fun ProfileNavigation() {
    val viewModel = hiltViewModel<ProfileTabViewModel>()
    val navController = rememberNavController()
    val startDestination =
        if (viewModel.authState())
            ProfileScreenRoute.ProfileScreen.name
        else
            ProfileScreenRoute.AuthScreen.name
    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composable(ProfileScreenRoute.ProfileScreen.name) {
            ProfileTabScreen(
                viewModel::addPhotoCallback,
                viewModel.uiState,
                navController,
                viewModel::logoutCallback,
                viewModel::navigateTo
            )
        }
        composable(ProfileScreenRoute.AuthScreen.name) {
            val authViewModel = hiltViewModel<AuthViewModel>()
            AuthTabScreen(
                uiState = authViewModel.uiState,
                forgetPassCallback = authViewModel::navigateTo,
                joinEmailCallback = authViewModel::joinEmailCallback,
                joinPasswordCallback = authViewModel::joinPasswordCallback,
                nameCallback = authViewModel::nameCallback,
                registerEmailCallback = authViewModel::registerEmailCallback,
                registerPasswordCallback = authViewModel::registerPasswordCallback,
                repeatPasswordCallback = authViewModel::repeatPasswordCallback,
                registerCallback = authViewModel::registerCallback,
                joinCallback = authViewModel::joinCallback
            )

        }
        composable(ProfileScreenRoute.EditProfile.name) {
            EditProfileScreen(
                addPhotoCallback = viewModel::addPhotoCallback,
                uiState = viewModel.uiState,
                popBackStackCallback = viewModel::popBackStack,
                navController = navController,
            )
        }
        composable(ProfileScreenRoute.ChangePassword.name) {
            ChangePasswordScreen(
                addPhotoCallback = viewModel::addPhotoCallback,
                uiState = viewModel.uiState,
                popBackStackCallback = viewModel::popBackStack,
                navController = navController
            )
        }
    }

}