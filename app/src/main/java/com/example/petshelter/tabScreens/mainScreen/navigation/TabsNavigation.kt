package com.example.petshelter.tabScreens.mainScreen.navigation

import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.example.petshelter.authScreens.main.components.AuthTabScreen
import com.example.petshelter.authScreens.main.viewModel.AuthViewModel
import com.example.petshelter.navigation.routeObject.MainScreenTabRoute
import com.example.petshelter.navigation.routeObject.createAnnouncementLoggedIn
import com.example.petshelter.navigation.routeObject.createAnnouncementLoggedOut
import com.example.petshelter.tabScreens.announcementTab.navigation.AnnouncementsAnimalNavigation
import com.example.petshelter.tabScreens.announcementTab.navigation.routeObject.animalIdParam
import com.example.petshelter.tabScreens.createAnnouncementTab.view.CreateAnnouncementTabScreen
import com.example.petshelter.tabScreens.createAnnouncementTab.viewModel.CreateAnnouncementTabViewModel
import com.example.petshelter.tabScreens.profileTab.navigation.ProfileNavigation


@ExperimentalFoundationApi
@Composable
fun TabsNavigation(
    navController: NavHostController,
    openDetailTab: (String) -> Unit,
    authState: Boolean
) {
    NavHost(navController, startDestination = MainScreenTabRoute.AnnouncementTab.name) {
        composable(
            route = MainScreenTabRoute.AnnouncementTab.name,
            arguments = listOf(navArgument(animalIdParam) { nullable = true })
        ) {
            Log.e("TAG", "TabsNavigation: ${it.arguments?.getString(animalIdParam)}")
            AnnouncementsAnimalNavigation(it.arguments?.getString(animalIdParam))
            it.arguments?.clear()
        }


        val startDestination =
            if (authState) createAnnouncementLoggedIn else createAnnouncementLoggedOut
        navigation(
            startDestination = startDestination,
            route = MainScreenTabRoute.CreateAnnouncementTab.name
        ) {
            composable(route = createAnnouncementLoggedIn) {
                val viewModel = hiltViewModel<CreateAnnouncementTabViewModel>()
                viewModel.setTabNavController(openDetailTab)
                CreateAnnouncementTabScreen(
                    viewModel.uiState,
                    viewModel::showPhoto,
                    viewModel::firstStepReady,
                    viewModel::secondStepReady,
                    viewModel::markerPositionMove,
                    viewModel::animalSelected,
                    viewModel::backArrowCallback,
                    viewModel::defaultValuesSelect,
                    viewModel::setTitleText,
                    viewModel::setDescriptionText,
                    viewModel::postAnnouncementBtn,
                )
            }
            composable(route = createAnnouncementLoggedOut) {
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
        }


        composable(route = MainScreenTabRoute.ProfileTab.name) {
            ProfileNavigation()

        }
    }
}
