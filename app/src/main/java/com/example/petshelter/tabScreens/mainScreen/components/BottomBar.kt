package com.example.petshelter.tabScreens.mainScreen.components

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.material.BottomAppBar
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.eql.consts.ui.colors.petShelterBlue
import com.example.petshelter.R
import com.example.petshelter.navigation.routeObject.announcementTab
import com.example.petshelter.navigation.routeObject.createAnnouncementLoggedIn
import com.example.petshelter.navigation.routeObject.createAnnouncementLoggedOut
import com.example.petshelter.navigation.routeObject.createAnnouncementTab
import com.example.petshelter.ui.styles.mainTabsSelectedTextStyle
import com.example.petshelter.ui.styles.mainTabsUnselectedTextStyle
import com.example.petshelter.ui.theme.PetShelterTheme

@Composable
fun MainPageBottomBar(
    currentRoute: String,
    onItemClick: ((String) -> Unit)? = null
) {
    val items = listOf(
        BottomBarItem.AnnouncementTabItem,
        BottomBarItem.CreateAnnouncementTabItem,
        BottomBarItem.ProfileTabItem
    )
    Log.e("TAG", "MainPageBottomBar: $currentRoute", )
    BottomAppBar(
        modifier = Modifier
            .fillMaxWidth()
            .height(77.dp),
        backgroundColor = petShelterBlue,
        contentPadding = PaddingValues(bottom = 10.dp)
    ) {
        items.forEach { item ->
            BottomNavigationItem(
                modifier = Modifier
                    .then(Modifier.weight(if (item == BottomBarItem.CreateAnnouncementTabItem) 1.25f else 1f)),
                icon = {
                    when ((currentRoute.toTabRoute() == item.toTabRoute())) {
                        true -> Icon(
                            painterResource(id = item.iconId),
                            contentDescription = item.route
                        )
                        false -> Icon(
                            painterResource(id = item.disableIconId),
                            contentDescription = item.route
                        )
                    }
                },
                selected = (currentRoute.toTabRoute() == item.toTabRoute()),
                alwaysShowLabel = true,
                label = {
                    Text(
                        modifier = Modifier,
                        text = when (item) {
                            is BottomBarItem.AnnouncementTabItem -> {
                                stringResource(R.string.announcements_tab)
                            }
                            is BottomBarItem.CreateAnnouncementTabItem -> {
                                stringResource(R.string.create_announce_tab_label)
                            }
                            is BottomBarItem.ProfileTabItem -> {
                                stringResource(R.string.profile_tab_label)
                            }
                        },
                        style = if (currentRoute.toTabRoute() == item.toTabRoute()||(currentRoute.contains(announcementTab)&&item.route.contains(announcementTab))) {
                            mainTabsSelectedTextStyle
                        } else {
                            mainTabsUnselectedTextStyle
                        },
                        textAlign = TextAlign.Center,
                        maxLines = 1,
                        softWrap = false

                    )
                },
                selectedContentColor = Color.White,
                onClick = {
                    if (currentRoute.toTabRoute() != item.toTabRoute())
                    onItemClick?.invoke(item.route)
                }
            )
        }
    }
}

@Preview
@Composable
fun BottomBarPreview() {
    PetShelterTheme {
        MainPageBottomBar(currentRoute = BottomBarItem.AnnouncementTabItem.route, {})
    }
}

private fun String.toTabRoute():String{
    return if(this== createAnnouncementLoggedIn||this== createAnnouncementLoggedOut)
        createAnnouncementTab
    else if(this.contains(announcementTab))
        announcementTab
    else
        this
}