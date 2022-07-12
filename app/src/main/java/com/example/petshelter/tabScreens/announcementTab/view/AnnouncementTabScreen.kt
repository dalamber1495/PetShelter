package com.example.petshelter.tabScreens.announcementTab.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.TabRowDefaults.tabIndicatorOffset
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.MutableLiveData
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberAsyncImagePainter
import com.eql.consts.ui.colors.backgroundPhotoColor
import com.eql.consts.ui.colors.petShelterBlue
import com.eql.consts.ui.colors.placeHolderColor
import com.example.petshelter.R
import com.example.petshelter.authScreens.main.consts.mainTabTextStyle
import com.example.petshelter.domain.model.Announcement
import com.example.petshelter.domain.model.AnnouncementsListState
import com.example.petshelter.tabScreens.announcementTab.common.AnimalsTabItem
import com.example.petshelter.tabScreens.announcementTab.model.AnnouncementTabUiState
import com.example.petshelter.tabScreens.announcementTab.navigation.routeObject.AnnouncementsScreenRoute
import com.example.petshelter.ui.styles.descriptionAnnounceTextStyle
import com.example.petshelter.ui.styles.titleAnnounceTextStyle
import com.example.petshelter.ui.theme.PetShelterTheme
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.PagerState
import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.launch

@OptIn(ExperimentalPagerApi::class)
@Composable
fun AnnouncementTabScreen(
    uiState: AnnouncementTabUiState,
    navController: NavController,
    navigateCallback: (NavController, AnnouncementsScreenRoute) -> Unit,
    selectAnnouncementCallback:(Int)->Unit
) {

    val announcements = uiState.animalsState.observeAsState(AnnouncementsListState())


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
                .shadow(elevation = 8.dp),
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
            announcements = announcements.value,
            navigateCallback = navigateCallback,
            selectAnnouncementCallback = selectAnnouncementCallback,
            navController = navController
        )
    }
}

@Composable
fun ListAnnouncements(
    announcements: AnnouncementsListState,
    navigateCallback: ( NavController,AnnouncementsScreenRoute) -> Unit,
    selectAnnouncementCallback:(Int)->Unit,
    navController: NavController
) {
    val announce = announcements.announcements
    if (announce.isNotEmpty()) {
        LazyVerticalGrid(
            modifier = Modifier.padding(horizontal = 8.dp),
            columns = GridCells.Fixed(2),
            content = {
                items(announce.size) {
                    Card(
                        modifier = Modifier
                            .padding(4.dp)
                            .clickable {
                                selectAnnouncementCallback.invoke(it)
                                navigateCallback.invoke(
                                    navController,
                                    AnnouncementsScreenRoute.DetailAnimalRoute
                                )
                            },
                        shape = RoundedCornerShape(8.dp),
                        elevation = 8.dp
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.SpaceBetween
                        ) {
                            BoxWithConstraints(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .background(backgroundPhotoColor)
                            ) {
                                Image(
                                    modifier = Modifier.height(maxWidth),
                                    painter = rememberAsyncImagePainter(
                                        model = announcements.announcements.first().imageUrl
                                            ?: R.drawable.ic_image_field
                                    ),
                                    contentDescription = null
                                )
                            }
                            Column(
                                modifier = Modifier.padding(14.dp),
                                verticalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(
                                    text = announcements.announcements.first().title,
                                    style = titleAnnounceTextStyle,
                                    maxLines = 1
                                )
                                Text(
                                    modifier = Modifier.padding(top = 4.dp, bottom = 7.dp),
                                    text = announcements.announcements.first().description,
                                    style = descriptionAnnounceTextStyle,
                                    maxLines = 2
                                )
                                Row {
                                    Image(
                                        modifier = Modifier.height(21.dp),
                                        painter = painterResource(id = R.drawable.ic_pet_marker),
                                        contentDescription = null
                                    )
                                    Text(
                                        text = "${announcements.announcements.first().geoPosition.lat} ${announcements.announcements.first().geoPosition.lng}",
                                        style = descriptionAnnounceTextStyle,
                                        color = placeHolderColor,
                                        maxLines = 2
                                    )

                                }
                            }
                        }
                    }
                }
            })
    }
    if(announcements.isLoading){
        CircularProgressIndicator(color = petShelterBlue)
    }
}

@OptIn(ExperimentalPagerApi::class, coil.annotation.ExperimentalCoilApi::class)
@Composable
fun AuthTabsContent(
    tabs: List<AnimalsTabItem>,
    pagerState: PagerState,
    announcements: AnnouncementsListState,
    navigateCallback: (NavController,AnnouncementsScreenRoute) -> Unit,
    selectAnnouncementCallback:(Int)->Unit,
    navController: NavController
) {

    HorizontalPager(
        state = pagerState,
        count = tabs.size,
        modifier = Modifier.fillMaxHeight(1f)
    ) { page ->
        when (tabs[page]) {
            is AnimalsTabItem.All -> {
                ListAnnouncements(announcements, navigateCallback, selectAnnouncementCallback,navController)
            }
            is AnimalsTabItem.Dogs -> {
                ListAnnouncements(announcements, navigateCallback, selectAnnouncementCallback,navController)
            }
            is AnimalsTabItem.Cats -> {
                ListAnnouncements(announcements, navigateCallback, selectAnnouncementCallback,navController)
            }
            is AnimalsTabItem.Other -> {
                ListAnnouncements(announcements, navigateCallback, selectAnnouncementCallback,navController)
            }
        }
    }

}

@Preview
@Composable
fun AnnouncementTabScreenPreview() {
    PetShelterTheme {
        AnnouncementTabScreen(
            AnnouncementTabUiState(
                animalsTabs = MutableLiveData(""),
                animalsState = MutableLiveData(AnnouncementsListState())
            ),
            navController = rememberNavController(),
            {w,t->},
            {}

        )
    }
}