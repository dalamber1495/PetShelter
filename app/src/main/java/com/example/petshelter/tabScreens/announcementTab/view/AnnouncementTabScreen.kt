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
import com.example.petshelter.domain.model.AnnouncementsListState
import com.example.petshelter.tabScreens.announcementTab.common.*
import com.example.petshelter.tabScreens.announcementTab.model.AnnouncementState
import com.example.petshelter.tabScreens.announcementTab.model.AnnouncementTabUiState
import com.example.petshelter.tabScreens.announcementTab.navigation.routeObject.AnnouncementsScreenRoute
import com.example.petshelter.ui.styles.descriptionAnnounceTextStyle
import com.example.petshelter.ui.styles.titleAnnounceTextStyle
import com.example.petshelter.ui.theme.PetShelterTheme
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.PagerState
import com.google.accompanist.pager.rememberPagerState
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import kotlinx.coroutines.launch

@OptIn(ExperimentalPagerApi::class)
@Composable
fun AnnouncementTabScreen(
    uiState: AnnouncementTabUiState,
    navController: NavController,
    navigateCallback: (NavController, AnnouncementsScreenRoute) -> Unit,
    selectAnnouncementCallback: (AnnouncementState) -> Unit,
    initAnnouncement: () -> Unit,
    id:String?,
    openDetail:(Int,NavController)->Unit

) {

    val announcements = uiState.animalsState.observeAsState(AnnouncementsListState())
    val listDogs = uiState.listDogs.observeAsState(AnnouncementsListState())
    val listCats = uiState.listCats.observeAsState(AnnouncementsListState())
    val listOther = uiState.listOther.observeAsState(AnnouncementsListState())
    val isRefreshing = uiState.isRefreshing.observeAsState(false)

    LaunchedEffect(key1 = id){
        id?.let {
            openDetail.invoke(it.toInt(),navController)
        }
    }

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
            listDogs = listDogs.value,
            listCats = listCats.value,
            listOther = listOther.value,
            navigateCallback = navigateCallback,
            selectAnnouncementCallback = selectAnnouncementCallback,
            refreshCallback = initAnnouncement,
            isRefreshing = isRefreshing.value,
            navController = navController
        )
    }
}

@Composable
fun ListAnnouncements(
    announcements: AnnouncementsListState,
    navigateCallback: (NavController, AnnouncementsScreenRoute) -> Unit,
    selectAnnouncementCallback: (AnnouncementState) -> Unit,
    isRefreshing: Boolean,
    refreshCallback: () -> Unit,
    navController: NavController
) {
    val announce = announcements.announcements

    SwipeRefresh(
        state = rememberSwipeRefreshState(isRefreshing = isRefreshing),
        onRefresh = refreshCallback
    ) {
//        if (announce.isNotEmpty()) {

            LazyVerticalGrid(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 8.dp),
                columns = GridCells.Fixed(2),
                contentPadding = PaddingValues(vertical = 15.dp),
                content = {
                    items(announce.size) {
                        Card(
                            modifier = Modifier
                                .padding(4.dp)
                                .clickable {
                                    selectAnnouncementCallback.invoke(announce[it])
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
                                        modifier = Modifier
                                            .height(maxWidth)
                                            .width(maxWidth),
                                        painter = rememberAsyncImagePainter(
                                            model = announcements.announcements[it].imageUrl
                                                ?: R.drawable.ic_image_field
                                        ),
                                        contentDescription = null,
                                    )
                                }
                                Column(
                                    modifier = Modifier.padding(14.dp),
                                    verticalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Text(
                                        text = announcements.announcements[it].title,
                                        style = titleAnnounceTextStyle,
                                        maxLines = 1
                                    )
                                    Text(
                                        modifier = Modifier.padding(top = 4.dp, bottom = 7.dp),
                                        text = announcements.announcements[it].description,
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
                                            text = announcements.announcements[it].address?:"${announcements.announcements[it].geoPosition.lat} ${announcements.announcements[it].geoPosition.lng}",
                                            style = descriptionAnnounceTextStyle,
                                            color = placeHolderColor,
                                            maxLines = 2
                                        )

                                    }
                                }
                            }
                        }
                    }
                }
            )
//        }
    }
    if (announcements.isLoading) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator(color = petShelterBlue)
        }
    }
}

@OptIn(ExperimentalPagerApi::class, coil.annotation.ExperimentalCoilApi::class)
@Composable
fun AuthTabsContent(
    tabs: List<AnimalsTabItem>,
    pagerState: PagerState,
    announcements: AnnouncementsListState,
    listDogs: AnnouncementsListState,
    listCats: AnnouncementsListState,
    listOther: AnnouncementsListState,
    navigateCallback: (NavController, AnnouncementsScreenRoute) -> Unit,
    selectAnnouncementCallback: (AnnouncementState) -> Unit,
    refreshCallback: () -> Unit,
    isRefreshing: Boolean,
    navController: NavController
) {

    HorizontalPager(
        state = pagerState,
        count = tabs.size,
        modifier = Modifier.fillMaxHeight()
    ) { page ->

        when (tabs[page]) {
            is AnimalsTabItem.All -> {
                ListAnnouncements(
                    announcements,
                    navigateCallback,
                    selectAnnouncementCallback,
                    isRefreshing,
                    refreshCallback,
                    navController
                )
            }
            is AnimalsTabItem.Dogs -> {
                ListAnnouncements(
                    listDogs,
                    navigateCallback,
                    selectAnnouncementCallback,
                    isRefreshing,
                    refreshCallback,
                    navController
                )
            }
            is AnimalsTabItem.Cats -> {
                ListAnnouncements(
                    listCats,
                    navigateCallback,
                    selectAnnouncementCallback,
                    isRefreshing,
                    refreshCallback,
                    navController
                )
            }
            is AnimalsTabItem.Other -> {
                ListAnnouncements(
                    listOther,
                    navigateCallback,
                    selectAnnouncementCallback,
                    isRefreshing,
                    refreshCallback,
                    navController
                )
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
                animalsState = MutableLiveData(AnnouncementsListState()),
                listDogs = MutableLiveData(AnnouncementsListState()),
                listCats = MutableLiveData(AnnouncementsListState()),
                listOther = MutableLiveData(AnnouncementsListState()),
                isRefreshing = MutableLiveData(false)
            ),
            navController = rememberNavController(),
            { w, t -> },
            {}, {},"",{e,r->}

        )
    }
}