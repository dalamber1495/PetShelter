package com.example.petshelter.tabScreens.mainScreen.navigation

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.petshelter.navigation.routeObject.MainScreenTabRoute
import com.example.petshelter.tabScreens.announcementTab.view.AnnouncementTabScreen
import com.example.petshelter.tabScreens.announcementTab.viewModel.AnnouncementTabViewModel
import com.example.petshelter.tabScreens.createAnnouncementTab.view.CreateAnnouncementTabScreen
import com.example.petshelter.tabScreens.createAnnouncementTab.viewModel.CreateAnnouncementTabViewModel
import com.example.petshelter.tabScreens.profileTab.view.ProfileTabScreen
import com.example.petshelter.tabScreens.profileTab.viewModel.ProfileTabViewModel
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.rememberPagerState

@OptIn(ExperimentalPagerApi::class)
@ExperimentalFoundationApi
@Composable
fun TabsNavigation(navController: NavHostController) {
    NavHost(navController, startDestination = MainScreenTabRoute.AnnouncementTab.name) {
        composable(route = MainScreenTabRoute.AnnouncementTab.name) {
            val viewModel = hiltViewModel<AnnouncementTabViewModel>()
            AnnouncementTabScreen(
            )
        }
        composable(route = MainScreenTabRoute.CreateAnnouncementTab.name) {
            val viewModel = hiltViewModel<CreateAnnouncementTabViewModel>()
            CreateAnnouncementTabScreen(

            )
        }
        composable(route = MainScreenTabRoute.ProfileTab.name) {
            val viewModel = hiltViewModel<ProfileTabViewModel>()
            ProfileTabScreen(
            )
        }
    }
}