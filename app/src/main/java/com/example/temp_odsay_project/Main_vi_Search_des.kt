package com.example.temp_odsay_project

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.temp_odsay_project.RetrofitModule.getRetrofit
import com.google.gson.annotations.SerializedName
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

data class SearchStationResponse(
    @SerializedName("result") val result: SearchResult
)

data class SearchResult(
    @SerializedName("totalCount") val totalCount: Int,
    @SerializedName("station") val station: List<SearchStationInfo>
)

data class SearchStationInfo(
    @SerializedName("stationClass") val stationClass: Int,
    @SerializedName("stationName") val stationName: String,
    @SerializedName("stationID") val stationID: Int,
    @SerializedName("x") val x: Double,
    @SerializedName("y") val y: Double,
    @SerializedName("arsID") val arsID: String,
    @SerializedName("busOnlyCentralLane") val busOnlyCentralLane: Int,
    @SerializedName("localStationID") val localStationID: String,
    @SerializedName("ebid") val ebid: String,
    @SerializedName("stationDirectionName") val stationDirectionName: String,
    @SerializedName("businfo") val busInfo: List<BusInfo>
)

data class BusInfo(
    @SerializedName("busClass") val busClass: String,
    @SerializedName("busLocalBlID") val busLocalBlID: String,
    @SerializedName("busNo") val busNo: String
)
interface SearchStationInterface{
    @GET("searchStation")
    fun getSearchStation(
        @Query("lang") lang: String, // 언어 코드 (예: "0")
        @Query("stationName") stationName: String, //정류장 이름
        @Query("stationClass") stationClass: Int, // 역 종류
        @Query("apiKey") apiKey: String // API 키
    ): Call<SearchStationResponse>
}
interface SearchStationView {
    // 검색 성공 시 호출될 메서드
    fun onSearchStationSuccess(response: SearchStationResponse)

    // 검색 실패 시 호출될 메서드
    fun onSearchStationFailure(errorMessage: String)
}
class SearchStationService {
    private lateinit var searchStationView: SearchStationView

    fun setSearchStationView(searchStationView: SearchStationView) {
        this.searchStationView = searchStationView
    }

    fun getSearchStation(lang: String, stationName: String, stationClass: Int, apiKey: String) {
        val searchStationService = getRetrofit().create(SearchStationInterface::class.java)
        searchStationService.getSearchStation(lang, stationName, stationClass, apiKey)
            .enqueue(object : Callback<SearchStationResponse> {
                override fun onResponse(
                    call: Call<SearchStationResponse>,
                    response: Response<SearchStationResponse>
                ) {
                    if (response.isSuccessful) {
                        val result: SearchStationResponse? = response.body()
                        if (result != null) {
                            // 서버 응답 코드에 따라 성공 또는 실패 처리
                            val count = result.result.totalCount
                            if (count > 0) {
                                searchStationView.onSearchStationSuccess(result)
                            } else {
                                searchStationView.onSearchStationFailure("주변에 역이 없습니다.")
                            }
                        } else {
                            Log.e("SEARCH-STATION-SUCCESS", "Response body is null")
                            searchStationView.onSearchStationFailure("응답이 없습니다.")
                        }
                    }else {
                        Log.e("SEARCH-STATION-SUCCESS", "Response not successful: $response")
                    }
                }

                override fun onFailure(call: Call<SearchStationResponse>, t: Throwable) {
                    Log.e("SEARCH-STATION-FAILURE", "Error: ${t.message}", t)
                    searchStationView.onSearchStationFailure("검색 중 오류가 발생했습니다.")
                }
            })
    }
}
interface OnItemClickListener {
    fun onItemClick(station: SearchStationInfo)
}
class SearchStationAdapter(private val itemClickListener: Main_vi_Search_des) : RecyclerView.Adapter<SearchStationAdapter.StationViewHolder>() {

    private var stationList: List<SearchStationInfo> = listOf() // 역 정보 리스트

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StationViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_station, parent, false)
        return StationViewHolder(view)
    }

    override fun onBindViewHolder(holder: StationViewHolder, position: Int) {
        val station = stationList[position]

        // 역 이름과 위치 정보를 ViewHolder의 View에 바인딩
        holder.stationNameTextView.text = station.stationName
        val location = "위도: ${station.y}, 경도: ${station.x}"
        holder.locationTextView.text = location
        val stationId = "stationId: ${station.stationID}"
        holder.stationIdView.text = stationId
        // 아이템 클릭 리스너 설정
        holder.itemView.setOnClickListener {
            itemClickListener.onItemClick(station)
        }
    }

    override fun getItemCount(): Int {
        return stationList.size
    }

    fun setStationList(stationList: List<SearchStationInfo>) {
        this.stationList = stationList
        notifyDataSetChanged()
    }

    class StationViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val stationNameTextView: TextView = itemView.findViewById(R.id.stationNameTextView)
        val locationTextView: TextView = itemView.findViewById(R.id.locationTextView)
        val stationIdView: TextView = itemView.findViewById(R.id.stationId)
    }
}


class Main_vi_Search_des : AppCompatActivity(), SearchStationView {
    private lateinit var recyclerView: RecyclerView
    private lateinit var searchStationAdapter: SearchStationAdapter
    private lateinit var searchStationService: SearchStationService
    private lateinit var searchEditText: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_vi_search_des)

        // RecyclerView 초기화
        recyclerView = findViewById(R.id.vi_drawer_rv)
        searchStationAdapter = SearchStationAdapter(itemClickListener = this)
        recyclerView.adapter = searchStationAdapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        // SearchStationService 초기화
        searchStationService = SearchStationService()
        searchStationService.setSearchStationView(this)

        // EditText 초기화
        searchEditText = findViewById(R.id.iv_main_search_place_et)

        // EditText에서 텍스트가 변경될 때마다 즉시 검색 수행
        searchEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                performSearch()
            }
        })
    }
    private fun performSearch() {
        val lang = "0" // 언어 코드
        val stationName = searchEditText.text.toString() // EditText에서 검색할 정류장 이름 가져오기
        val stationClass = 1 // 역 종류
        val apiKey = "HPHPd7QcYbOiPd61vVYcvc0iXnhHZCyfNHh6l9UqGj4" // API 키
        searchStationService.getSearchStation(lang, stationName, stationClass, apiKey)
    }

    override fun onSearchStationSuccess(response: SearchStationResponse) {
        searchStationAdapter.setStationList(response.result.station)
    }

    override fun onSearchStationFailure(errorMessage: String) {
        // 서버 응답 실패 시 처리
        // 예시로 실패 메시지를 출력합니다.
        println(errorMessage)
    }
    fun onItemClick(station: SearchStationInfo) {
        GlobalValues_end.endPointStation = station
        // 클릭된 항목의 정보를 로그로 출력
        println("--Station Info--")
        println("stationName: ${station.stationName}")
        println("stationID: ${station.stationID}")
        println("x: ${station.x}")
        println("y: ${station.y}")
        val intent = Intent(this, searchPubTransPath::class.java)
        startActivity(intent)
    }

    object GlobalValues_end {
        var endPointStation: SearchStationInfo? = null
    }
}
