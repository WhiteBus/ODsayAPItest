package com.example.temp_odsay_project

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.temp_odsay_project.remote.dto.PathResult
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.Callback
import retrofit2.Response


interface PathInterface {
    @GET("searchPubTransPath")
    fun getpathBusNum(
        @Query("lang") lang: String, // 언어 코드 (예: "0")
        @Query("SX") SX:Double,	//출발지 X좌표 (경도좌표)
        @Query("SY") SY:Double, //출발지 Y좌표 (위도좌표)
        @Query("EX") EX:Double,//도착지 X좌표 (경도좌표)
        @Query("EY") EY:Double, //도착지 Y좌표 (위도좌표)
        @Query("SearchPathType") SearchPathType:Int, //도시 내 경로수단을 지정 (버스 = 2)
        @Query("apiKey") apiKey: String // API 키
    ): Call<PathResult>
}
interface PathView {
    // 검색 성공 시 호출될 메서드
    fun onSearchStationSuccess(response: PathResult)

    // 검색 실패 시 호출될 메서드
    fun onSearchStationFailure(errorMessage: String)
}

class PathService(private val retrofit: Retrofit) {

    private val pathInterface: PathInterface = retrofit.create(PathInterface::class.java)

    fun searchPath(
        lang: String,
        SX: Double,
        SY: Double,
        EX: Double,
        EY: Double,
        SearchPathType: Int,
        apiKey: String,
        callback: PathView
    ) {
        pathInterface.getpathBusNum(lang, SX, SY, EX, EY, SearchPathType, apiKey)
            .enqueue(object : Callback<PathResult> {
                override fun onResponse(call: Call<PathResult>, response: Response<PathResult>) {
                    if (response.isSuccessful) {
                        response.body()?.let {
                            callback.onSearchStationSuccess(it)
                        } ?: run {
                            callback.onSearchStationFailure("Response body is null.")
                        }
                    } else {
                        callback.onSearchStationFailure("Failed to get response.")
                    }
                }

                override fun onFailure(call: Call<PathResult>, t: Throwable) {
                    callback.onSearchStationFailure(t.message ?: "Unknown error occurred.")
                }
            })
    }
}

class searchPubTransPath : AppCompatActivity(), PathView {

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
            pathService.searchPath("0", SX, SY, EX, EY, 2, "HPHPd7QcYbOiPd61vVYcvc0iXnhHZCyfNHh6l9UqGj4", this)
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



