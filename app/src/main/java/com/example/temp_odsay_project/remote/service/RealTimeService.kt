package com.example.temp_odsay_project.remote.service

import com.example.temp_odsay_project.remote.dto.RealtimeBusArrivalRes
import com.example.temp_odsay_project.remote.retrofit.BusArrivalInterface
import com.example.temp_odsay_project.remote.view.PathView
import com.example.temp_odsay_project.remote.view.RealTimeView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit

class RealTimeService(private val retrofit: Retrofit) {
    private var realTimeView: RealTimeView?=null
    private val busArrivalService = retrofit.create(BusArrivalInterface::class.java)

    fun setRealtimeBusArrival(view: RealTimeView){
        this.realTimeView = view
    }

    fun getRealtimeBusArrival(
        lang: Int,
        stationID: Int,
        apiKey: String,
        callback: (RealtimeBusArrivalRes) -> Unit,
        errorCallback: (String) -> Unit
    ) {
        val call = busArrivalService.getRealtimeBusArrival(lang, stationID, apiKey)
        call.enqueue(object : Callback<RealtimeBusArrivalRes> {
            override fun onResponse(call: Call<RealtimeBusArrivalRes>, response: Response<RealtimeBusArrivalRes>) {
                if (response.isSuccessful) {
                    response.body()?.let {
                        callback(it)
                    } ?: errorCallback("No data")
                } else {
                    errorCallback(response.message())
                }
            }

            override fun onFailure(call: Call<RealtimeBusArrivalRes>, t: Throwable) {
                errorCallback(t.message ?: "Unknown error")
            }
        })
    }
}