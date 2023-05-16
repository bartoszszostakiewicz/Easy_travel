package com.project.easy_travel.remote

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.ktx.Firebase
import com.project.easy_travel.Model.Trip
import com.project.easy_travel.Model.User
import com.project.easy_travel.repository.MainRepository
import com.project.easy_travel.repository.UserRepository


class UserViewModel : ViewModel() {
    private val repository = UserRepository.getInstance()
    private val _data = MutableLiveData<User>()
    val data: LiveData<User> get() = _data

    fun setData(user: User) {
        _data.value = user
    }


    fun getAllItems(): LiveData<List<User>> {
        return repository.getAllItems()
    }

    fun getById(id: String): LiveData<User?> {
        return repository.getById(id)
    }

    fun save(user: User, id: String = "") {
        repository.save(user, id)
    }

    fun delete(id: String) {
        repository.delete(id)
    }
}
