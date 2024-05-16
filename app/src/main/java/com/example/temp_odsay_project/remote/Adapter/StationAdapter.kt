package com.example.temp_odsay_project.remote.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.temp_odsay_project.R
import com.example.temp_odsay_project.remote.dto.Station

class StationAdapter(private val onItemClick: (Station) -> Unit) : RecyclerView.Adapter<StationAdapter.StationViewHolder>() {

    private var stationList: List<Station> = listOf() // 역 정보 리스트

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

        // 아이템이 클릭되었을 때, onItemClick 콜백 실행
        holder.itemView.setOnClickListener {
            onItemClick(station)
        }
    }

    override fun getItemCount(): Int {
        return stationList.size
    }

    fun setStationList(stationList: List<Station>) {
        this.stationList = stationList
        notifyDataSetChanged()
    }

    class StationViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val stationNameTextView: TextView = itemView.findViewById(R.id.stationNameTextView)
        val locationTextView: TextView = itemView.findViewById(R.id.locationTextView)
        val stationIdView: TextView = itemView.findViewById(R.id.stationId)
    }
<<<<<<< HEAD
}
=======

}

>>>>>>> 0566bbe87eaa3afcd78eb3ab3a2bbf8c14de1aaa
