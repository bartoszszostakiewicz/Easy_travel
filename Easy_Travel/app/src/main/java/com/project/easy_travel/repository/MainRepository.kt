package com.project.easy_travel.repository

class MainRepository{

    private val userRepository: UserRepository = UserRepository()
    private val tripRepository: TripRepository = TripRepository()
    private val pointRepository : PointRepository = PointRepository()

    fun getUserRepository(): UserRepository {
        return userRepository
    }

    fun getTripRepository(): TripRepository {
        return tripRepository
    }
    fun getPointRepository(): PointRepository {
        return pointRepository
    }
}