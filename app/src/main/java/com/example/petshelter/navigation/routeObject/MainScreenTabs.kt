package com.example.petshelter.navigation.routeObject

import com.example.petshelter.tabScreens.announcementTab.navigation.routeObject.animalIdParam

const val announcementTab = "announcementTab"
const val createAnnouncementTab = "createAnnouncementTab"
const val createAnnouncementLoggedIn = "createAnnouncementLoggedIn"
const val createAnnouncementLoggedOut = "createAnnouncementLoggedOut"
const val profileTab = "profileGraph"

sealed class MainScreenTabRoute(var name: String) {
    object AnnouncementTab: MainScreenTabRoute("$announcementTab?animalId={$animalIdParam}"){
        fun createRoute(animalId: String) {
            name = "$announcementTab?animalId={$animalId}"
        }
    }
    object CreateAnnouncementTab: MainScreenTabRoute(createAnnouncementTab)
    object ProfileTab: MainScreenTabRoute(profileTab)
}
