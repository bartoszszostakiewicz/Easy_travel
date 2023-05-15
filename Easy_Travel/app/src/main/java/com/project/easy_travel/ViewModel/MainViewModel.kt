package com.project.easy_travel.ViewModel

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.project.easy_travel.Model.User
import com.project.easy_travel.remote.UserViewModel
import com.project.easy_travel.repository.MainRepository

class MainViewModel : Application() {
    val userViewModel: UserViewModel by lazy {
        ViewModelProvider.AndroidViewModelFactory.getInstance(this).create(UserViewModel::class.java)
    }

    val tripViewModel: TripViewModel by lazy {
        ViewModelProvider.AndroidViewModelFactory.getInstance(this).create(TripViewModel::class.java)
    }

    val tripPointViewModel: TripPointViewModel by lazy {
        ViewModelProvider.AndroidViewModelFactory.getInstance(this).create(TripPointViewModel::class.java)
    }

}