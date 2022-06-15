package com.example.petshelter.tabScreens.announcementTab.common

private const val allRoute = "Все"
private const val dogsRoute = "Собаки"
private const val catsRoute = "Кошки"
private const val otherRoute = "Другие"

sealed class AnimalsTabItem(var route:String){
    object All: AnimalsTabItem(allRoute)
    object Dogs: AnimalsTabItem(dogsRoute)
    object Cats: AnimalsTabItem(catsRoute)
    object Other: AnimalsTabItem(otherRoute)

}
