package com.example.temp_odsay_project

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.temp_odsay_project.remote.Adapter.TransitAdapter
import com.example.temp_odsay_project.remote.dto.PathInfoStation

//class TransitInfoActivity : AppCompatActivity() {
//
//    private lateinit var binding: ActivityTransitInfoBinding
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        binding = ActivityTransitInfoBinding.inflate(layoutInflater)
//        setContentView(binding.root)
//
//        // 데이터 받기
//        val pathInfoList = intent.getParcelableArrayListExtra<PathInfoStation>("pathInfoList")
//        val selectedPathIndex = intent.getIntExtra("selectedPathIndex", -1)
//
//        if (pathInfoList != null && selectedPathIndex != -1) {
//            val selectedPathInfo = pathInfoList[selectedPathIndex]
//
//            // 상단 정보 설정
//            binding.userAddressDstName.text = "최종목적지"
//            binding.userAddress.text = "${selectedPathInfo.firstStartStation} -> ${selectedPathInfo.lastEndStation}"
//            binding.userDistance.text = "거리: ${selectedPathInfo.subPaths.sumBy { it.distance }}m"
//            binding.userTime.text = "총 시간: ${selectedPathInfo.totalTime}분"
//
//            // RecyclerView 설정
//            binding.transitRecyclerView.layoutManager = LinearLayoutManager(this)
//            binding.transitRecyclerView.adapter = TransitAdapter(selectedPathInfo.subPaths)
//        }
//    }
//}