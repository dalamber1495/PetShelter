package com.example.petshelter.startScreen

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.petshelter.navigation.AppNavigation
import com.example.petshelter.navigation.graphs.authGraph
import com.example.petshelter.navigation.graphs.mainFlowGraph
import com.example.petshelter.navigation.routeObject.AppScreens
import com.example.petshelter.navigation.routeObject.popRouteName
import com.example.petshelter.ui.notLoggedUserGraph
import com.example.petshelter.ui.theme.PetShelterTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @Inject
    lateinit var navigator: AppNavigation

    @OptIn(ExperimentalComposeUiApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val startingDestination = AppScreens.MainAppScreen.route

        val startingGraph = notLoggedUserGraph

        setContent {
            val navigationController = rememberNavController()
            LaunchedEffect(Unit) {
                navigator.navRoute.onEach {
                    when (it.route) {
                        popRouteName -> {
                            when (it.popTargetRoute.isEmpty()) {
                                true -> navigationController.popBackStack()
                                false -> navigationController.popBackStack(
                                    it.popTargetRoute,
                                    it.inclusive,
                                    it.saveState
                                )
                            }
                        }
                        else -> navigationController.navigate(it.route, it.options)
                    }
                }.launchIn(this)
            }

            NavHost(navController = navigationController,
                startDestination = AppScreens.SplashScreen.route){

                authGraph(navigationController)
                mainFlowGraph(navigationController)

                composable(AppScreens.SplashScreen.route){
                    SplashScreen(navController = navigationController,startingGraph = startingGraph)
                }


            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    PetShelterTheme {
    }
}