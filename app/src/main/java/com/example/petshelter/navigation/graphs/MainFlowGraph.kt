package com.example.petshelter.navigation.graphs

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.example.petshelter.navigation.routeObject.AppScreens
import com.example.petshelter.navigation.routeObject.loggedUserGraph
import com.example.petshelter.tabScreens.mainScreen.view.MainScreen
import com.example.petshelter.tabScreens.mainScreen.viewModel.MainScreenViewModel


@OptIn(ExperimentalFoundationApi::class)
fun NavGraphBuilder.mainFlowGraph(navController: NavController) {
    navigation(startDestination = AppScreens.MainAppScreen.route, route = loggedUserGraph) {
        composable(route = AppScreens.MainAppScreen.route) {
            val viewModel = hiltViewModel<MainScreenViewModel>()
            MainScreen(
                uiState = viewModel.uiState,
                setTabRouteCallback = viewModel::setCurrentTabRoute
            )
        }


    }
}