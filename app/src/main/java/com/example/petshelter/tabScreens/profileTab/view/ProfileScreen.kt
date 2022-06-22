package com.example.petshelter.tabScreens.profileTab.view

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.petshelter.tabScreens.mainScreen.navigation.TabsNavigation
import com.example.petshelter.tabScreens.profileTab.navigation.ProfileNavigation
import com.example.petshelter.tabScreens.profileTab.navigation.routeObject.ProfileScreenRoute

@Composable
fun ProfileScreen(){

    val navController = rememberNavController()
//    val navBackStackEntry by navController.currentBackStackEntryAsState()
//    val currentRoute = navBackStackEntry?.destination?.route

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        ProfileNavigation(navController = navController)
    }
}