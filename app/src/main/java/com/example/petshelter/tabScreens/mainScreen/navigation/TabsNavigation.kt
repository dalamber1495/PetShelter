package com.example.petshelter.tabScreens.mainScreen.navigation

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraph
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.petshelter.navigation.routeObject.MainScreenTabRoute
import com.example.petshelter.tabScreens.announcementTab.view.AnnouncementTabScreen
import com.example.petshelter.tabScreens.announcementTab.view.AnnouncementsHost
import com.example.petshelter.tabScreens.announcementTab.viewModel.AnnouncementTabViewModel
import com.example.petshelter.tabScreens.createAnnouncementTab.view.CreateAnnouncementTabScreen
import com.example.petshelter.tabScreens.createAnnouncementTab.viewModel.CreateAnnouncementTabViewModel
import com.example.petshelter.tabScreens.profileTab.navigation.routeObject.ProfileScreenRoute
import com.example.petshelter.tabScreens.profileTab.view.ChangePasswordScreen
import com.example.petshelter.tabScreens.profileTab.view.EditProfileScreen
import com.example.petshelter.tabScreens.profileTab.view.ProfileScreen
import com.example.petshelter.tabScreens.profileTab.view.ProfileTabScreen
import com.example.petshelter.tabScreens.profileTab.viewModel.ProfileTabViewModel
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.rememberPagerState

@ExperimentalFoundationApi
@Composable
fun TabsNavigation(navController: NavHostController) {
    NavHost(navController, startDestination = MainScreenTabRoute.AnnouncementTab.name) {
        composable(route = MainScreenTabRoute.AnnouncementTab.name) {
            AnnouncementsHost()
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
            ProfileScreen(
            )
        }
    }
}
