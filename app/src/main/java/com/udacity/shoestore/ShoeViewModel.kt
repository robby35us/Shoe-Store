package com.udacity.shoestore

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.udacity.shoestore.models.Shoe

class ShoeViewModel : ViewModel() {
    val shoeList = MutableLiveData<MutableList<Shoe>>()

    init {
        shoeList.value = mutableListOf(
            Shoe("Black/Blue Boots", 10.5, "Timberland", "Some cool, comfortable boots with alloy toe I got for working in the warehouse at amazon."),
            Shoe("Pride Checkered Flats", 10.5, "Vans", "White and rainbow checkered pride shoes from Vans. A little dirty but well loved!"),
            Shoe("Old Running Shoes", 10.5, "Walmart", "Just some old dark blue and grey running shoes. Pretty beat up. Definitely time for some new ones"))
    }
}