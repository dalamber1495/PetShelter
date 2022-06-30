package com.example.petshelter.tabScreens.mainScreen.navigation

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.petshelter.navigation.routeObject.MainScreenTabRoute
import com.example.petshelter.tabScreens.announcementTab.navigation.AnnouncementsAnimalNavigation
import com.example.petshelter.tabScreens.createAnnouncementTab.view.CreateAnnouncementTabScreen
import com.example.petshelter.tabScreens.createAnnouncementTab.viewModel.CreateAnnouncementTabViewModel
import com.example.petshelter.tabScreens.profileTab.navigation.ProfileNavigation


@ExperimentalFoundationApi
@Composable
fun TabsNavigation(navController: NavHostController) {
    NavHost(navController, startDestination = MainScreenTabRoute.AnnouncementTab.name) {
        composable(route = MainScreenTabRoute.AnnouncementTab.name) {
            AnnouncementsAnimalNavigation()
        }
        composable(route = MainScreenTabRoute.CreateAnnouncementTab.name) {
            val viewModel = hiltViewModel<CreateAnnouncementTabViewModel>()

            CreateAnnouncementTabScreen(
                viewModel.uiState,
                {}, viewModel::showPhoto,
                viewModel::firstStepReady,
                viewModel::secondStepReady,
                viewModel::markerPositionMove,
                viewModel::animalSelected
            )
        }
        composable(route = MainScreenTabRoute.ProfileTab.name) {
            ProfileNavigation()

        }
    }
}
