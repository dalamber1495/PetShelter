package com.example.petshelter.tabScreens.announcementTab.navigation.routeObject

import androidx.navigation.NavOptions

const val detailAnimalRoute = "detailAnimalRoute"
const val animalIdParam = "animalId"
const val announcementsRoute = "announcementsRoute"
const val mapLocateRoute = "mapLocateRoute"

sealed class AnnouncementsScreenRoute(
    var route: String,
    val options: NavOptions? = null,
    val inclusive: Boolean = false,
    val saveState: Boolean = false,
    val popTargetRoute: String = ""
) {

    object AnnouncementsRoute : AnnouncementsScreenRoute(
        announcementsRoute,
        options = NavOptions.Builder().setPopUpTo(0, inclusive = false, saveState = true)
            .setLaunchSingleTop(true).setRestoreState(true).build()
    )

    object DetailAnimalRoute : AnnouncementsScreenRoute(detailAnimalRoute)
    object MapLocateRoute : AnnouncementsScreenRoute(mapLocateRoute)
}
