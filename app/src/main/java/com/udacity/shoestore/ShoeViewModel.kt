package com.udacity.shoestore

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.udacity.shoestore.models.Shoe

class ShoeViewModel : ViewModel() {
    private val _shoeList = MutableLiveData<MutableList<Shoe>>()
    val shoeList : LiveData<MutableList<Shoe>>
        get() = _shoeList

    private val _shoeDataComplete = MutableLiveData<Boolean>()
    val shoeDataComplete : LiveData<Boolean>
        get() = _shoeDataComplete

    private val _invalidShoeSize = MutableLiveData<Boolean>()
    val invalidShoeSize : LiveData<Boolean>
        get() = _invalidShoeSize

    private var newShoeName = ""
    private var newShoeCompany = ""
    private var newShoeSize = 0.0
    private var newShoeDescrption = ""

    init {
        _shoeDataComplete.value = false
        _shoeList.value = mutableListOf()
    }
            // dummy data
//            Shoe("Black/Blue Boots", 10.5, "Timberland", "Some cool, comfortable boots with alloy toe I got for working in the warehouse at amazon."),
//            Shoe("Pride Checkered Flats", 10.5, "Vans", "White and rainbow checkered pride shoes from Vans. A little dirty but well loved!"),
//            Shoe("Old Running Shoes", 10.5, "Walmart", "Just some old dark blue and grey running shoes. Pretty beat up. Definitely time for some new ones"))
//

    fun setShoeName(name: String) {
        newShoeName = name
        checkShoeDataComplete()
    }

    fun setShoeCompany(company: String) {
        newShoeCompany = company
        checkShoeDataComplete()
    }

    fun setShoeSize(size: String) {
        try {
            newShoeSize = size.toDouble()
            checkShoeDataComplete()
        } catch (e: NumberFormatException) {
            newShoeSize = 0.0
            _invalidShoeSize.value = true
        }
    }

    fun setShoeDescription(description: String) {
        newShoeDescrption = description
        checkShoeDataComplete()
    }

    private fun checkShoeDataComplete() {
        _shoeDataComplete.value =
            !newShoeName.equals("") &&
            !newShoeCompany.equals("") &&
            !newShoeDescrption.equals("") &&
            newShoeSize > 0.0
    }

    fun clearShoeData() {
        newShoeName = ""
        newShoeCompany = ""
        newShoeSize = 0.0
        newShoeDescrption = ""
        _shoeDataComplete.value = false
    }

    fun addShoeToList() {
        _shoeList.value?.add(0, Shoe(newShoeName, newShoeSize, newShoeCompany, newShoeDescrption))
    }

    fun onInvalidSizeCompleted() {
        _invalidShoeSize.value = false
    }
}