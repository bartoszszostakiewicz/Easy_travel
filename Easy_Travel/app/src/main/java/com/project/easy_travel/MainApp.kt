package com.project.easy_travel

import android.app.Application
import androidx.lifecycle.ViewModelProvider
import com.project.easy_travel.ViewModel.MainViewModel
import com.project.easy_travel.remote.UserViewModel

class MainApp : Application() {

    override fun onCreate() {
        super.onCreate()
        instance = this
    }

    companion object {
        lateinit var instance: MainApp
            private set
    }
    private val userViewModel: UserViewModel by lazy {
        ViewModelProvider.AndroidViewModelFactory.getInstance(this).create(UserViewModel::class.java)
    }

    fun userViewModelGet(): UserViewModel {
        return userViewModel
    }
}