package com.example.petshelter.tabScreens.mainScreen.components

import com.example.petshelter.R
import com.example.petshelter.navigation.routeObject.announcementTab
import com.example.petshelter.navigation.routeObject.createAnnouncementTab
import com.example.petshelter.navigation.routeObject.profileTab


sealed class BottomBarItem(val route: String, val iconId: Int, val disableIconId:Int) {
    object AnnouncementTabItem : BottomBarItem(announcementTab,R.drawable.ic_announcetab_active ,R.drawable.ic_announcetab_disactive)
    object CreateAnnouncementTabItem : BottomBarItem(createAnnouncementTab,R.drawable.ic_create_announce_active ,R.drawable.ic_create_announce_disable)
    object ProfileTabItem : BottomBarItem(profileTab,R.drawable.ic_profiletab_active ,R.drawable.ic_profiletab_disactive)
}
