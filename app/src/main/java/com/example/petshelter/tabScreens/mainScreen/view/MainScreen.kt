package com.example.petshelter.tabScreens.mainScreen.view

import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.petshelter.tabScreens.announcementTab.navigation.routeObject.animalIdParam
import com.example.petshelter.tabScreens.mainScreen.components.BottomBarItem
import com.example.petshelter.tabScreens.mainScreen.components.MainPageBottomBar
import com.example.petshelter.tabScreens.mainScreen.model.MainScreenUIState
import com.example.petshelter.tabScreens.mainScreen.navigation.TabsNavigation

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MainScreen(
    uiState: MainScreenUIState,
    setTabRouteCallback: (String) -> Unit,
    authState:Boolean
) {
    val currentTab = uiState.currentTabRoute.observeAsState()
    val tabsNavigator = rememberNavController()
    val navBackStackEntry by tabsNavigator.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    val openDetailTab :(String)->Unit = {id ->
        Log.e("TAG", "MainScreen: $id", )
        val destination = BottomBarItem.AnnouncementTabItem
        destination.createRoute(id)
        tabsNavigator.navigate(BottomBarItem.AnnouncementTabItem.route){
            this.popUpTo(0) {
                inclusive = false
                saveState = true
            }
        }
        setTabRouteCallback(destination.route)
        destination.createRoute(animalIdParam)
    }
    Scaffold(
        bottomBar = {
            MainPageBottomBar(
                currentRoute?:BottomBarItem.AnnouncementTabItem.route
            ) {
                tabsNavigator.navigate(it) {
                    tabsNavigator.graph.startDestinationRoute?.let { route ->
                        popUpTo(route) {
                            saveState = true
                        }
                    }
                    launchSingleTop = true
                    restoreState = true
                    setTabRouteCallback(it)
                }
            }
        }
    ) {
        Box(
            modifier = Modifier.padding(
                PaddingValues(0.dp, 0.dp, 0.dp, it.calculateBottomPadding())
            )
        ) {
            TabsNavigation(navController = tabsNavigator, openDetailTab,authState)
        }
    }


}