package com.amirreza.osmiumproject.ui.viewmodel

import androidx.lifecycle.*
import com.amirreza.osmiumproject.data.repository.CellInfoRepository
import com.amirreza.osmiumproject.ui.model.CellInfo
import kotlinx.coroutines.launch

class CellInfoViewModel(private val repository: CellInfoRepository) : ViewModel() {

    val allCellInfo: LiveData<List<CellInfo>> = repository.allCellInfo.asLiveData()

    fun insert(cellInfo: CellInfo) = viewModelScope.launch {
        repository.insert(cellInfo)
    }

    // Function to get all records with a specific cellId
    fun getCellInfoByCellId(cellId: Int): LiveData<List<CellInfo>> {
        return repository.getCellInfoByCellId(cellId).asLiveData()
    }

    suspend fun getCellInfo(cellId: Int, lac: Int, signalStrength: Int, latitude: Double, longitude: Double): CellInfo? {
        return repository.getCellInfo(cellId, lac, signalStrength, latitude, longitude)
    }
}

class CellInfoViewModelFactory(private val repository: CellInfoRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CellInfoViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return CellInfoViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
