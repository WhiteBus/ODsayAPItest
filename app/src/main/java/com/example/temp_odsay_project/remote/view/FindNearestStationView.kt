package com.example.temp_odsay_project.remote.view

import com.example.temp_odsay_project.remote.dto.FindNearestStationGetRes

interface FindNearestStationView {
    fun onFindNearestStationSuccess(response: FindNearestStationGetRes)
    fun onFindNearestStationFailure(errorMessage: String, response: FindNearestStationGetRes?)
}