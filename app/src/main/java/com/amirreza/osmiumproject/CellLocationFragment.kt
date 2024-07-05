package com.amirreza.osmiumproject

import UEData
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.amirreza.osmiumproject.databinding.FragmentCellLocationBinding
import getCellLocation

class CellLocationFragment : Fragment() {
    private var _binding: FragmentCellLocationBinding? = null
    private val binding get() = _binding!!
    private val cellInfoViewModel: CellInfoViewModel by viewModels {
        CellInfoViewModelFactory((requireActivity().application as OsmiumApplication).repository)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCellLocationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        cellInfoViewModel.allCellInfo.observe(viewLifecycleOwner, { cellInfo ->
            Log.d("CellLocationFragment", "cellInfo: $cellInfo")
            if (cellInfo != null && cellInfo.isNotEmpty()) {
                val ueDataList = cellInfo.map {
                    UEData(it.latitude, it.longitude, it.signalStrength.toDouble())
                }
                Log.d("CellLocationFragment", "UEDataList: $ueDataList")
                val cellLocation = getCellLocation(ueDataList)
                Log.d("CellLocationFragment", "cellLocation: $cellLocation")
                if (cellLocation != null) {
                    // فرض می‌کنیم از اولین CellInfo برای نمایش Cell ID استفاده می‌کنیم
                    val cellId = cellInfo.first().cellId
                    binding.cellIdText.text = "Cell ID: $cellId"
                    binding.locationText.text = "Location: ${cellLocation.first}, ${cellLocation.second}"
                } else {
                    binding.cellIdText.text = "Cell ID: N/A"
                    binding.locationText.text = "Location: N/A"
                }
            } else {
                binding.cellIdText.text = "Cell ID: N/A"
                binding.locationText.text = "Location: N/A"
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}