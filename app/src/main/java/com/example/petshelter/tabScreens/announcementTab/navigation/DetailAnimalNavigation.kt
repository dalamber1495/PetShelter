package com.example.petshelter.tabScreens.announcementTab.navigation

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.petshelter.navigation.routeObject.MainScreenTabRoute
import com.example.petshelter.tabScreens.announcementTab.navigation.routeObject.AnnouncementsScreenRoute
import com.example.petshelter.tabScreens.announcementTab.navigation.routeObject.animalIdParam
import com.example.petshelter.tabScreens.announcementTab.view.AnnouncementTabScreen
import com.example.petshelter.tabScreens.announcementTab.view.DetailAnimalScreen
import com.example.petshelter.tabScreens.announcementTab.view.MapLocationScreen
import com.example.petshelter.tabScreens.announcementTab.viewModel.AnnouncementTabViewModel

@Composable
fun AnnouncementsAnimalNavigation(id:String?){
    val viewModel = hiltViewModel<AnnouncementTabViewModel>()
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = AnnouncementsScreenRoute.AnnouncementsRoute.route){

        composable(AnnouncementsScreenRoute.AnnouncementsRoute.route){
            Log.e("TAG", "AnnouncementsAnimalNavigation: ", )
            viewModel.setNavController(navController)
            id?.let {
                viewModel.openDetail(it.toInt())
                MainScreenTabRoute.AnnouncementTab.createRoute(animalIdParam)
            }
            AnnouncementTabScreen(
                viewModel.uiState,
                navController,
                viewModel::navigateTo,
                viewModel::selectAnnouncementCallback,
                viewModel::initAnnouncement
            )
        }

        composable(route = AnnouncementsScreenRoute.DetailAnimalRoute.route) {

            DetailAnimalScreen(
                selectedAnnouncementState = viewModel.detailUiState,
                navController = navController,
                navigateCallback = viewModel::navigateTo,
                popBackStack = viewModel::popBackStack,
                snackbarState = viewModel.snackbarState,
                snackBarOffCallback = viewModel::snackBarOffCallback
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