package com.example.temp_odsay_project.remote.dto

data class TransitInfo(
    val busNo: String,
    val startName: String,
    val endName: String,
    val distance: Double,
    val sectionTime: Int
)
