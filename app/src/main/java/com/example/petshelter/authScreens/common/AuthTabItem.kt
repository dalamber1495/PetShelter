package com.example.petshelter.authScreens.common


private const val registerRoute = "Регистрация"
private const val signInRoute = "Вход"

sealed class AuthTabItem(var route:String){
        object Register: AuthTabItem(registerRoute)
        object SignIn: AuthTabItem(signInRoute)
}
