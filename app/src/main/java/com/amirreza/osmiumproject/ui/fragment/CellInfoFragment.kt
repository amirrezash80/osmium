package com.amirreza.osmiumproject.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.amirreza.osmiumproject.data.OsmiumApplication
import com.amirreza.osmiumproject.databinding.FragmentCellInfoBinding
import com.amirreza.osmiumproject.ui.viewmodel.CellInfoViewModel
import com.amirreza.osmiumproject.ui.viewmodel.CellInfoViewModelFactory
import com.amirreza.osmiumproject.ui.adapter.CellInfoAdapter

class CellInfoFragment : Fragment() {
    private var _binding: FragmentCellInfoBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: CellInfoAdapter
    private val cellInfoViewModel: CellInfoViewModel by viewModels {
        CellInfoViewModelFactory((requireActivity().application as OsmiumApplication).repository)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCellInfoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = CellInfoAdapter()
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.adapter = adapter

        cellInfoViewModel.allCellInfo.observe(viewLifecycleOwner, { cellInfo ->
            cellInfo?.let { adapter.setCellInfoList(it) }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
