package com.example.petshelter.tabScreens.mainScreen.view

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController
import com.example.petshelter.tabScreens.mainScreen.components.BottomBarItem
import com.example.petshelter.tabScreens.mainScreen.components.MainPageBottomBar
import com.example.petshelter.tabScreens.mainScreen.model.MainScreenUIState
import com.example.petshelter.tabScreens.mainScreen.navigation.TabsNavigation

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MainScreen(
    uiState: MainScreenUIState,
    setTabRouteCallback: (String) -> Unit
) {
    val currentTab = uiState.currentTabRoute.observeAsState()
    val tabsNavigator = rememberNavController()

    Scaffold(
        bottomBar = {
            MainPageBottomBar(
                currentTab.value ?: BottomBarItem.AnnouncementTabItem.route
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
            TabsNavigation(navController = tabsNavigator)
        }
    }


}