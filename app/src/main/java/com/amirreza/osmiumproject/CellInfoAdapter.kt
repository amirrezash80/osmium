package com.amirreza.osmiumproject

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.amirreza.osmiumproject.databinding.ItemCellInfoBinding

class CellInfoAdapter : RecyclerView.Adapter<CellInfoAdapter.CellInfoViewHolder>() {

    private var cellInfoList: List<CellInfo> = emptyList()

    fun setCellInfoList(cellInfoList: List<CellInfo>) {
        this.cellInfoList = cellInfoList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CellInfoViewHolder {
        val binding = ItemCellInfoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CellInfoViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CellInfoViewHolder, position: Int) {
        holder.bind(cellInfoList[position])
    }

    override fun getItemCount(): Int = cellInfoList.size

    inner class CellInfoViewHolder(private val binding: ItemCellInfoBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(cellInfo: CellInfo) {
            binding.cellIdText.text = "Cell ID: ${cellInfo.cellId}"
            binding.lacText.text = "LAC: ${cellInfo.lac}"
            binding.signalStrengthText.text = "Signal Strength: ${cellInfo.signalStrength} dBm"
            binding.latitudeText.text = "Latitude: ${cellInfo.latitude}"
            binding.longitudeText.text = "Longitude: ${cellInfo.longitude}"
        }
    }
}
