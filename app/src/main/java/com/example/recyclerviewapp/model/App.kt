package com.example.recyclerviewapp.model

import android.app.Application

class App : Application() {
    val userService = UserService()
}