package com.example.temp_odsay_project

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.temp_odsay_project.remote.dto.PathResult
import com.example.temp_odsay_project.remote.retrofit.PathInterface
import com.example.temp_odsay_project.remote.service.PathService
import com.example.temp_odsay_project.remote.view.PathView
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.Callback
import retrofit2.Response


<<<<<<< HEAD
class Main_searchPubPathT: AppCompatActivity(), PathView {
=======

class Main_searchPubPathT : AppCompatActivity(), PathView {
>>>>>>> 0566bbe87eaa3afcd78eb3ab3a2bbf8c14de1aaa

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.item_subpath)

        // 출발지와 도착지의 좌표를 설정
        val startStationX = MainActivity.GlobalValue_start.longitude
        val startStationY = MainActivity.GlobalValue_start.latitude
        val endStation = Main_vi_Search_des.GlobalValues_end.endPointStation

        if (startStationY != null && startStationX != null && endStation != null) {
            // Retrofit을 사용하여 API 호출
            val retrofit = RetrofitModule.getRetrofit()
            val pathService = PathService(retrofit)

            // 출발지와 도착지의 좌표를 가져와서 API에 전달
            val SX = startStationX
            val SY = startStationY
            val EX = endStation.x
            val EY = endStation.y

            // API 호출
            pathService.searchPath("0", SX, SY, EX, EY, 2, "rfSg7BEmSQsZFsPbMswlSOp5iiDu6smXQXY56n+aR4U", this)
        } else {
            // 출발지 또는 도착지가 설정되지 않은 경우 처리
            println("Please select both start and end stations.")
        }
    }

    override fun onSearchStationSuccess(response: PathResult) {

        val searchInfo = response.result

        // 경로 정보 출력
        val paths = searchInfo.paths
        if (paths.isNotEmpty()) {
            for (path in paths) {
                val pathInfo = path.pathInfo

                // PathInfo 정보 출력
                println("---")
                println("Traffic Distance: ${pathInfo.trafficDistance}")
                println("Total Time: ${pathInfo.totalTime}")
                println("Bus Transit Count: ${pathInfo.busTransitCount}")

                // SubPath 정보 출력
                val subPaths = path.subPaths
                for (subPath in subPaths) {
                    println("Distance: ${subPath.distance}")

                    // Lane 정보 출력
                    val lanes = subPath.lane
                    lanes?.forEach { lane ->
                        println("Bus Number: ${lane.busNo}")
                    }
                }
            }
        } else {
            println("No path information available.")
        }
    }


    override fun onSearchStationFailure(errorMessage: String) {
        // 실패 시 처리
        println("Error occurred: $errorMessage")
    }

}



