package com.apprajapati.livedata

import androidx.lifecycle.ViewModel

class MyViewModel : ViewModel() {

    val myData = OwnLiveData(12)

    fun increaseMyData() {
        myData.increaseValue()
    }
}