package com.example.temp_odsay_project.remote.view

import com.example.temp_odsay_project.remote.dto.PathResult
import com.example.temp_odsay_project.remote.dto.SearchStationResponse

interface TransitPathView {
    // 검색 성공 시 호출될 메서드
    fun onTransitPathSuccess(response: PathResult, startX: Double, startY: Double, endX: Double, endY: Double)

    // 검색 실패 시 호출될 메서드
    fun onTransitPathFailure(errorMessage: String)
}