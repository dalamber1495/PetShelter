package com.example.petshelter.navigation.routeObject

const val announcementTab = "announcementTab"
const val createAnnouncementTab = "createAnnouncementTab"

const val profileTab = "profileTab"

sealed class MainScreenTabRoute(val name: String) {
    object AnnouncementTab: MainScreenTabRoute(announcementTab)
    object CreateAnnouncementTab: MainScreenTabRoute(createAnnouncementTab)
    object ProfileTab: MainScreenTabRoute(profileTab)
}
