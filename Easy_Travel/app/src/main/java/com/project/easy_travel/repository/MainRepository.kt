package com.project.easy_travel.repository

class MainRepository{

    private val userRepository: UserRepository = UserRepository()
    private val tripRepository: TripRepository = TripRepository()

    fun getUserRepository(): UserRepository {
        return userRepository
    }

    fun getTripRepository(): TripRepository {
        return tripRepository
    }
}