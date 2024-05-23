package com.example.temp_odsay_project.remote.view

import com.example.temp_odsay_project.remote.dto.PathResult

interface PathView {
    // 검색 성공 시 호출될 메서드

    fun onSearchPathSuccess(response: PathResult, startX: Double, startY: Double, endX: Double, endY: Double)
    fun onSearchPathFailure(errorMessage: String)

}