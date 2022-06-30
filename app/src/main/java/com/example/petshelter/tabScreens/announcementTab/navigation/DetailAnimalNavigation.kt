package com.example.petshelter.tabScreens.announcementTab.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.petshelter.tabScreens.announcementTab.navigation.routeObject.AnnouncementsScreenRoute
import com.example.petshelter.tabScreens.announcementTab.navigation.routeObject.animalIdParam
import com.example.petshelter.tabScreens.announcementTab.view.AnnouncementTabScreen
import com.example.petshelter.tabScreens.announcementTab.view.DetailAnimalScreen
import com.example.petshelter.tabScreens.announcementTab.view.MapLocationScreen
import com.example.petshelter.tabScreens.announcementTab.viewModel.AnnouncementTabViewModel

@Composable
fun AnnouncementsAnimalNavigation(){
    val viewModel = hiltViewModel<AnnouncementTabViewModel>()
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = AnnouncementsScreenRoute.AnnouncementsRoute.route){

        composable(AnnouncementsScreenRoute.AnnouncementsRoute.route){
            AnnouncementTabScreen(
                viewModel.uiState,
                navController,
                viewModel::navigateTo,
                viewModel::selectAnnouncementCallback
            )
        }

        composable(route = AnnouncementsScreenRoute.DetailAnimalRoute.route) {
            val animalId = it.arguments?.getString(animalIdParam) ?: ""
            DetailAnimalScreen(
                selectedAnnouncementState = viewModel.detailUiState,
                navController,
                viewModel::navigateTo,
                viewModel::popBackStack
            )
        }
        composable(route = AnnouncementsScreenRoute.MapLocateRoute.route) {
            MapLocationScreen(
                selectedAnnouncementState = viewModel.detailUiState,
                navController = navController,
                popBackStack = viewModel::popBackStack
            )
        }
    }
}