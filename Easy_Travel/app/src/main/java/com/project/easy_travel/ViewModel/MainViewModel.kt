package com.project.easy_travel.ViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.project.easy_travel.remote.UserViewModel
import com.project.easy_travel.repository.MainRepository

class MainViewModel(private val mainRepository: MainRepository) : ViewModel() {
    private val _userViewModel: MutableLiveData<UserViewModel> = MutableLiveData()
    private val _tripViewModel: MutableLiveData<TripViewModel> = MutableLiveData()

    val userViewModel: LiveData<UserViewModel>
        get() = _userViewModel

    val tripViewModel: LiveData<TripViewModel>
        get() = _tripViewModel

    fun initUserViewModel(userId: String) {
        val userRepository = mainRepository.getUserRepository()
        val userViewModel = UserViewModel(/*userId, */userRepository)
        _userViewModel.value = userViewModel
    }
}