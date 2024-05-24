package com.example.temp_odsay_project.remote.service

import com.example.temp_odsay_project.remote.dto.PathResult
import com.example.temp_odsay_project.remote.retrofit.PathInterface
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit


class TransitPathService(private val retrofit: Retrofit) {
//    fun TransitSearchPath(
//        lang: String,
//        SX: Double,
//        SY: Double,
//        EX: Double,
//        EY: Double,
//        searchPathType: Int,
//        apiKey: String,
//        transitPathView: TransitPathView
//    ): Call<PathResult> {
//        val service = retrofit.create(PathInterface::class.java)
//        val call = service.getpathBusNum(lang, SX, SY, EX, EY, searchPathType, apiKey)
//
//        call.enqueue(object : Callback<PathResult> {
//            override fun onResponse(call: Call<PathResult>, response: Response<PathResult>) {
//                if (response.isSuccessful && response.body() != null) {
//                    transitPathView.onTransitPathSuccess(response.body()!!, SX, SY, EX, EY)
//                } else {
//                    transitPathView.onTransitPathFailure(response.message())
//                }
//            }
//
//            override fun onFailure(call: Call<PathResult>, t: Throwable) {
//                transitPathView.onTransitPathFailure(t.message ?: "Unknown error")
//            }
//        })
//        return call
//    }
}