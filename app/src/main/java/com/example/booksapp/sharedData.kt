package com.example.booksapp

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class sharedData : ViewModel() {
    val sharedID = MutableLiveData<String>()
}