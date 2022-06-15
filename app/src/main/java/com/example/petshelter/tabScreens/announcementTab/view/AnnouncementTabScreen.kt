package com.example.petshelter.tabScreens.announcementTab.view

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.TabRowDefaults.tabIndicatorOffset
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.eql.consts.ui.colors.petShelterBlue
import com.example.petshelter.authScreens.main.consts.mainTabTextStyle
import com.example.petshelter.tabScreens.announcementTab.common.AnimalsTabItem
import com.example.petshelter.ui.theme.PetShelterTheme
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.PagerState
import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.launch

@OptIn(ExperimentalPagerApi::class)
@Composable
fun AnnouncementTabScreen(
) {

    val tabs = listOf(
        AnimalsTabItem.All,
        AnimalsTabItem.Dogs,
        AnimalsTabItem.Cats,
        AnimalsTabItem.Other
    )
    val pagerState = rememberPagerState()
    val scope = rememberCoroutineScope()
    Column {

        TabRow(
            modifier = Modifier
                .fillMaxWidth()
                .height(80.dp)
                .shadow(elevation = 8.dp)
            ,
            indicator = { tabPositions ->
                TabRowDefaults.Indicator(
                    Modifier
                        .tabIndicatorOffset(tabPositions[pagerState.currentPage])
                        .height(5.dp)
                )
            },
            selectedTabIndex = pagerState.currentPage,
            backgroundColor = petShelterBlue,
            contentColor = Color.White,
            divider = {
                TabRowDefaults.Divider(
                    color = Color.White,
                    modifier = Modifier,
                    thickness = 2.dp
                )
            }
        ) {
            tabs.forEachIndexed { index, tab ->
                val selected = pagerState.currentPage == index
                Tab(
                    modifier = Modifier,
                    text = {
                        Column(
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {

                            Text(
                                text = tab.route,
                                style = mainTabTextStyle
                            )
                        }
                    },
                    selected = selected,
                    onClick = {
                        scope.launch {
                            pagerState.animateScrollToPage(index)
                        }
                    },
                )
            }
        }
        AuthTabsContent(
            tabs = tabs,
            pagerState = pagerState,
        )
    }
}

@OptIn(ExperimentalPagerApi::class, coil.annotation.ExperimentalCoilApi::class)
@Composable
fun AuthTabsContent(
    tabs: List<AnimalsTabItem>,
    pagerState: PagerState,
) {
    HorizontalPager(
        state = pagerState,
        count = tabs.size,
        modifier = Modifier.fillMaxHeight(0.9f)
    ) { page ->
        when (tabs[page]) {
            is AnimalsTabItem.All -> {
            }
            is AnimalsTabItem.Dogs -> {
            }
            is AnimalsTabItem.Cats -> {
            }
            is AnimalsTabItem.Other -> {
            }
        }
    }

}
@Preview
@Composable
fun AnnouncementTabScreenPreview(){
    PetShelterTheme {
        AnnouncementTabScreen()
    }
}