package com.example.petshelter.tabScreens.announcementTab.navigation.routeObject

const val detailAnimalRoute = "detailAnimalRoute"
const val animalIdParam = "animalParam"
const val announcementsRoute = "announcementsRoute"
sealed class AnnouncementsScreenRoute (var route:String){

    object AnnouncementsRoute:AnnouncementsScreenRoute(announcementsRoute)
    object DetailAnimalRoute: AnnouncementsScreenRoute("$detailAnimalRoute/{$animalIdParam}") {
        fun createRoute(animalId: String) {
            route = "$detailAnimalRoute/$animalIdParam"
        }
    }
}
