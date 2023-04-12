package com.project.easy_travel.repository

import androidx.lifecycle.LiveData
import com.project.easy_travel.Model.User
import com.project.easy_travel.remote.UserSource

object UserRepository {
    private val userSource = UserSource()
    fun get(userId: String): LiveData<User> = userSource.get(userId)
    fun save(user: User) = userSource.save(user)
    fun delete(userId: String) = userSource.delete(userId)

}
//interface UserRepository {
//    fun get(userId: String): LiveData<User>
//    fun save(user: User)
//    fun delete(userId: String)
//}