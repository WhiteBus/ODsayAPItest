package com.example.temp_odsay_project.remote.view;

import com.example.temp_odsay_project.remote.dto.SearchStationResponse;

interface SearchStationView {
    // 검색 성공 시 호출될 메서드
    fun onSearchStationSuccess(response:SearchStationResponse)

    // 검색 실패 시 호출될 메서드
    fun onSearchStationFailure(errorMessage: String)
}
