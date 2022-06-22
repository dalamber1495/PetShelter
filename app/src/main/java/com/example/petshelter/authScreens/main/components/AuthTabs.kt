package com.example.petshelter.authScreens.main.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CornerBasedShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.TabRowDefaults.tabIndicatorOffset
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.eql.consts.ui.colors.petShelterBlack
import com.eql.consts.ui.colors.petShelterBlue
import com.eql.consts.ui.colors.petShelterWhite
import com.example.petshelter.R
import com.example.petshelter.authScreens.common.AuthTabItem
import com.example.petshelter.authScreens.main.consts.joinTextStyle
import com.example.petshelter.authScreens.main.consts.tabTextStyle
import com.example.petshelter.navigation.routeObject.AppScreens
import com.example.petshelter.ui.theme.Shapes
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.PagerState
import com.google.accompanist.pager.pagerTabIndicatorOffset
import kotlinx.coroutines.launch

@OptIn(ExperimentalPagerApi::class)
@Composable
fun AuthTabs(
    tabs: List<AuthTabItem>,
    pagerState: PagerState,
    forgetPassCallback: (AppScreens) -> Unit
) {
    val scope = rememberCoroutineScope()
    TabRow(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp),
        indicator = { tabPositions ->
            TabRowDefaults.Indicator(
                Modifier
                    .tabIndicatorOffset(tabPositions[pagerState.currentPage])
                    .height(5.dp)
            )
        },
        selectedTabIndex = pagerState.currentPage,
        backgroundColor = Color.Transparent,
        contentColor = petShelterBlue,
        divider = {
            TabRowDefaults.Divider(color = petShelterBlue, modifier = Modifier.width(5.dp))
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
                            color = petShelterBlack,
                            style = tabTextStyle
                        )
                        Spacer(modifier = Modifier.height(18.dp))
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
        forgetPassCallback
    )
}

@OptIn(ExperimentalPagerApi::class, coil.annotation.ExperimentalCoilApi::class)
@Composable
fun AuthTabsContent(
    tabs: List<AuthTabItem>,
    pagerState: PagerState,
    forgetPassCallback: (AppScreens) -> Unit
) {
    HorizontalPager(
        state = pagerState,
        count = tabs.size,
        modifier = Modifier.fillMaxHeight(0.9f)
    ) { page ->
        when (tabs[page]) {
            is AuthTabItem.SignIn -> {
                Surface(
                    modifier = Modifier
                        .fillMaxHeight()
                        .fillMaxWidth()
                        .padding()
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.SpaceAround
                    ) {
                        Column {
                            FormField(modifier = Modifier
                                .height(56.dp),
                                placeHolder = "Email",
                                valueCallback = {})
                            Spacer(modifier = Modifier.height(32.dp))
                            FormField(modifier = Modifier
                                .height(56.dp),
                                placeHolder = "Пароль",
                                visibleIcon = true,
                                valueCallback = {})
                        }

                        PetShelterBtn(
                            modifier = Modifier
                                .width(147.dp)
                                .height(60.dp),
                            image = R.drawable.ic_lpet_btn,
                            text = "Войти",
                            clickCallback = {})
                        TextButton(modifier = Modifier.wrapContentSize(),
                            onClick = { forgetPassCallback.invoke(AppScreens.ForgetPass) }) {
                            Row {
                                Text("Забыли пароль?", style = joinTextStyle)
                            }
                        }
                    }
                }
            }
            is AuthTabItem.Register -> {
                Surface(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight()
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.SpaceAround
                    ) {
                        Column {
                            FormField(modifier = Modifier
                                .height(56.dp),
                                placeHolder = "Имя",
                                valueCallback = {})
                            Spacer(modifier = Modifier.height(32.dp))
                            FormField(modifier = Modifier
                                .height(56.dp),
                                placeHolder = "Email",
                                valueCallback = {})
                            Spacer(modifier = Modifier.height(32.dp))
                            FormField(modifier = Modifier
                                .height(56.dp),
                                placeHolder = "Пароль",
                                visibleIcon = true,
                                valueCallback = {})
                            Spacer(modifier = Modifier.height(32.dp))
                            FormField(modifier = Modifier
                                .height(56.dp),
                                placeHolder = "Повторите пароль",
                                visibleIcon = true,
                                valueCallback
                                = {})
                        }
                        PetShelterBtn(modifier = Modifier
                            .wrapContentWidth()
                            .height(60.dp),
                            text = "Зарегистрироваться",
                            image = R.drawable.ic_lpet_btn,
                            clickCallback = {})
                    }
                }
            }
        }
    }
}