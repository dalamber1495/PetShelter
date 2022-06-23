package com.example.petshelter.tabScreens.announcementTab.view

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.example.petshelter.tabScreens.announcementTab.navigation.AnnouncementsAnimalNavigation

@Composable
fun AnnouncementsHost()
{

    val navController = rememberNavController()
//    val navBackStackEntry by navController.currentBackStackEntryAsState()
//    val currentRoute = navBackStackEntry?.destination?.route

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        AnnouncementsAnimalNavigation(navController = navController)
    }
}