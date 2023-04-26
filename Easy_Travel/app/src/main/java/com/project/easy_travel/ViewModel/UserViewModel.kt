package com.project.easy_travel.remote

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.project.easy_travel.Model.User
import com.project.easy_travel.repository.MainRepository
import com.project.easy_travel.repository.UserRepository


class UserViewModel(
    private val userRepository: UserRepository = MainRepository.getUserRepository()
    ) : ViewModel()
{

    fun getResponse(userId: String) : LiveData<User?>{
        return  userRepository.getLiveData(userId)
    }

}
