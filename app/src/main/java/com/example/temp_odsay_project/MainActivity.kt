package com.example.temp_odsay_project

import FindNearestStationService
import com.example.temp_odsay_project.remote.Adapter.StationAdapter
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.temp_odsay_project.remote.dto.FindNearestStationGetRes
import com.example.temp_odsay_project.remote.dto.Station
import com.example.temp_odsay_project.remote.view.FindNearestStationView

class MainActivity : AppCompatActivity(), FindNearestStationView {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: StationAdapter
    private lateinit var findNearestStationService: FindNearestStationService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = StationAdapter()
        recyclerView.adapter = adapter

        findNearestStationService = FindNearestStationService()
        findNearestStationService.setNearestStationGetView(this)

        // 이 예시에서는 하드코딩된 위치 정보와 apiKey를 사용합니다. 실제로는 사용자의 위치 정보와 apiKey를 적절히 가져와야 합니다.
        val lang = "0" // 언어 코드
        val longitude = 127.126378 // 경도
        val latitude = 37.449711 // 위도
        val radius = 400 // 검색 반경 (미터)
        val stationClass = 1 // 역 종류 (1: 지하철역)
        val apiKey = "rfSg7BEmSQsZFsPbMswlSOp5iiDu6smXQXY56n+aR4U"

        // FindNearestStationService를 사용하여 역 정보 가져오기
        findNearestStationService.getNearestStation(lang, longitude, latitude, radius, stationClass, apiKey)
    }

    override fun onFindNearestStationSuccess(response: FindNearestStationGetRes) {
        // 서버에서 받은 역 정보를 RecyclerView에 표시
        adapter.setStationList(response.result.station)
    }

    override fun onFindNearestStationFailure(errorMessage: String, response: FindNearestStationGetRes?) {
        // 실패 시 처리
        // 예: Toast 또는 AlertDialog로 사용자에게 실패 메시지 표시
    }

    object GlobalValue_start{
        var startPointStation: Station?=null
        val longitude: Double = 127.126378 // 경도
        val latitude: Double = 37.449711 // 위도
    }

}
