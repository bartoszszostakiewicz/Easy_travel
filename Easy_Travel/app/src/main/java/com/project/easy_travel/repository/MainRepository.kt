package com.project.easy_travel.repository

object MainRepository{

    private val userRepository: UserRepository = TODO()

    fun getUserRepository(): UserRepository {
        return userRepository
    }

    //fun getTripRepository(): TripRepository {
    //    return tripRepository
    //}
}