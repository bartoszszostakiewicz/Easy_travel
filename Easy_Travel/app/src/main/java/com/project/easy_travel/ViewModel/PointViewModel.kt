package com.project.easy_travel.remote
/*
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.project.easy_travel.Model.Point
import com.project.easy_travel.Model.User
import com.project.easy_travel.repository.MainRepository
import com.project.easy_travel.repository.PointRepository
import com.project.easy_travel.repository.UserRepository


class PointViewModel(
    private val pointRepository: PointRepository = MainRepository.getPointRepository()
    ) : ViewModel()
{

    fun getResponse(userId: String) : LiveData<Point?>{
        return  pointRepository.getLiveData(userId)
    }

    fun getResponses(userId: List<String>) : List<LiveData<Point?>>{
        return  pointRepository.getMultipleLiveData(userId)
    }
}
**/