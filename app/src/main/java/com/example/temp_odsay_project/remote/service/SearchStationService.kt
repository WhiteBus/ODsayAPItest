package com.example.temp_odsay_project.remote.service

import com.example.temp_odsay_project.RetrofitModule.getRetrofit
import com.example.temp_odsay_project.remote.dto.SearchStationResponse
import com.example.temp_odsay_project.remote.retrofit.SearchStationInterface
import com.example.temp_odsay_project.remote.view.SearchStationView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SearchStationService {
    private var searchStationView: SearchStationView? = null
    private val searchStationInterface = getRetrofit().create(SearchStationInterface::class.java)

    fun setSearchStationView(view: SearchStationView) {
        this.searchStationView = view
    }

    fun getSearchStation(lang: String, stationName: String, stationClass: Int, apiKey: String) {
        searchStationInterface.getSearchStation(lang, stationName, stationClass, apiKey)
            .enqueue(object : Callback<SearchStationResponse> {
                override fun onResponse(
                    call: Call<SearchStationResponse>,
                    response: Response<SearchStationResponse>
                ) {
                    if (response.isSuccessful) {
                        val stationResponse = response.body()
                        stationResponse?.let {
                            searchStationView?.onSearchStationSuccess(it)
                        }
                    } else {
                        searchStationView?.onSearchStationFailure("Failed to get station data")
                    }
                }

                override fun onFailure(call: Call<SearchStationResponse>, t: Throwable) {
                    searchStationView?.onSearchStationFailure(t.message ?: "Unknown error")
                }
            })
    }
}
