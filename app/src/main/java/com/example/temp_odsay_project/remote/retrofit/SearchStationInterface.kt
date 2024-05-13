package com.example.temp_odsay_project.remote.retrofit

import com.example.temp_odsay_project.remote.dto.SearchStationResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface SearchStationInterface{
    @GET("searchStation")
    fun getSearchStation(
        @Query("lang") lang: String, // 언어 코드 (예: "0")
        @Query("stationName") stationName: String, //정류장 이름
        @Query("stationClass") stationClass: Int, // 역 종류
        @Query("apiKey") apiKey: String // API 키
    ): Call<SearchStationResponse>
}