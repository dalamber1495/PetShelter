package com.example.petshelter.tabScreens.announcementTab.common

const val allRoute = "Все"
const val dogsRoute = "Собаки"
const val catsRoute = "Кошки"
const val otherRoute = "Другие"

sealed class AnimalsTabItem(var route:String){
    object All: AnimalsTabItem(allRoute)
    object Dogs: AnimalsTabItem(dogsRoute)
    object Cats: AnimalsTabItem(catsRoute)
    object Other: AnimalsTabItem(otherRoute)

}
