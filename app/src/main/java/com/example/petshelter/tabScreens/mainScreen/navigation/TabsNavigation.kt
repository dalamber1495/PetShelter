package com.example.petshelter.tabScreens.mainScreen.navigation

import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.petshelter.navigation.routeObject.MainScreenTabRoute
import com.example.petshelter.tabScreens.announcementTab.navigation.AnnouncementsAnimalNavigation
import com.example.petshelter.tabScreens.announcementTab.navigation.routeObject.animalIdParam
import com.example.petshelter.tabScreens.createAnnouncementTab.view.CreateAnnouncementTabScreen
import com.example.petshelter.tabScreens.createAnnouncementTab.viewModel.CreateAnnouncementTabViewModel
import com.example.petshelter.tabScreens.profileTab.navigation.ProfileNavigation


@ExperimentalFoundationApi
@Composable
fun TabsNavigation(navController: NavHostController,openDetailTab: (String)->Unit) {
    NavHost(navController, startDestination = MainScreenTabRoute.AnnouncementTab.name) {
        composable(route = MainScreenTabRoute.AnnouncementTab.name,
        arguments = listOf(navArgument(animalIdParam){nullable = true})) {
            Log.e("TAG", "TabsNavigation: ${it.arguments?.getString(animalIdParam)}", )
            AnnouncementsAnimalNavigation(it.arguments?.getString(animalIdParam))
            it.arguments?.clear()
        }
        composable(route = MainScreenTabRoute.CreateAnnouncementTab.name) {
            val viewModel = hiltViewModel<CreateAnnouncementTabViewModel>()
            viewModel.setTabNavController(navController, openDetailTab)
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
                viewModel::postAnnouncementBtn
            )
        }
        composable(route = MainScreenTabRoute.ProfileTab.name) {
            ProfileNavigation()

        }
    }
}
