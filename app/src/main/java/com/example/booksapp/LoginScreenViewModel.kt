package com.example.booksapp

import androidx.lifecycle.ViewModel

class LoginScreenViewModel : ViewModel() {
    var usersList: MutableList<User> = mutableListOf()
}