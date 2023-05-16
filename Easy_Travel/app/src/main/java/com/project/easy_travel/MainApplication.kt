package com.project.easy_travel

import android.app.Application
import androidx.lifecycle.ViewModelProvider
import com.project.easy_travel.ViewModel.TripPointViewModel
import com.project.easy_travel.ViewModel.TripViewModel
import com.project.easy_travel.remote.UserViewModel

class MainApplication: Application() {
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