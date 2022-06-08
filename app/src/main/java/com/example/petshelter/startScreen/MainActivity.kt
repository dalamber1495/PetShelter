package com.example.petshelter.startScreen

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.petshelter.navigation.routeObject.AppScreens
import com.example.petshelter.ui.theme.PetShelterTheme
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val startingDestination = AppScreens.MainAppScreen.route


        setContent {
            val navigationController = rememberNavController()
            NavHost(navController = navigationController,
                startDestination = AppScreens.SplashScreen.route){

                composable(AppScreens.SplashScreen.route){
                    SplashScreen(navController = navigationController)
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