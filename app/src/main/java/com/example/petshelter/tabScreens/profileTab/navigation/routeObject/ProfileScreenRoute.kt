package com.example.petshelter.tabScreens.profileTab.navigation.routeObject

const val editProfile = "editProfile"
const val changePassword = "changePassword"
const val profileScreen = "profileScreen"


sealed class ProfileScreenRoute(val name: String) {
    object ProfileScreen : ProfileScreenRoute(profileScreen)
    object EditProfile : ProfileScreenRoute(editProfile)
    object ChangePassword : ProfileScreenRoute(changePassword)
}
