package com.project.easy_travel.remote

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.database.*
import com.project.easy_travel.Model.User
import com.project.easy_travel.repository.UserRepository
import kotlinx.coroutines.launch


class UserViewModel(private val userRepository: UserRepository, private val userId: String) : ViewModel()
{
    private val _user: MutableLiveData<User> = MutableLiveData()

    val user: LiveData<User>
        get() = _user

    init {
        load()
    }

    private fun load() {
//        viewModelScope.launch {
//            userRepository.get(userId)
//                .onStart { /* Komunikat o rozpoczeciu ładowania */ }
//                .catch { /* Komunikat o błędzie */ }
//                .collect { _user.value = user }
//        }
        val userLiveData = userRepository.get(userId)
        userLiveData.observeForever { user ->
            _user.value = user
        }
    }

    fun save(user: User) {
        viewModelScope.launch() {
            userRepository.save(user)
        }
    }
}
