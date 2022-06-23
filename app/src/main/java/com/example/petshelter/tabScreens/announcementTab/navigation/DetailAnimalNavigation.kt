package com.example.petshelter.tabScreens.announcementTab.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.petshelter.tabScreens.announcementTab.navigation.routeObject.AnnouncementsScreenRoute
import com.example.petshelter.tabScreens.announcementTab.navigation.routeObject.animalIdParam
import com.example.petshelter.tabScreens.announcementTab.view.AnnouncementTabScreen
import com.example.petshelter.tabScreens.announcementTab.view.DetailAnimalScreen
import com.example.petshelter.tabScreens.announcementTab.viewModel.AnnouncementTabViewModel
import com.example.petshelter.tabScreens.announcementTab.viewModel.DetailAnimalViewModel

@Composable
fun AnnouncementsAnimalNavigation(navController: NavHostController){

    NavHost(navController = navController, startDestination = AnnouncementsScreenRoute.AnnouncementsRoute.route){

        composable(AnnouncementsScreenRoute.AnnouncementsRoute.route){
            AnnouncementTabScreen(
            )
        }

        composable(route = AnnouncementsScreenRoute.DetailAnimalRoute.route) {
            val animalId = it.arguments?.getString(animalIdParam) ?: ""
            val viewModel = hiltViewModel<DetailAnimalViewModel>()
            viewModel.animalId = animalId

            DetailAnimalScreen(
                uiState = viewModel.uiState,
                backButtonCallback = viewModel::backButtonCallback,
                initCallback = viewModel::loadAnimalData,
            )
        }
    }
}