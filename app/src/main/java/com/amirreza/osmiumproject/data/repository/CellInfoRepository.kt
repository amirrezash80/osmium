package com.amirreza.osmiumproject.data.repository

import androidx.annotation.WorkerThread
import com.amirreza.osmiumproject.ui.model.CellInfo
import com.amirreza.osmiumproject.data.database.CellInfoDao
import kotlinx.coroutines.flow.Flow

class CellInfoRepository(private val cellInfoDao: CellInfoDao) {

    val allCellInfo: Flow<List<CellInfo>> = cellInfoDao.getAllCellInfo()

    @WorkerThread
    suspend fun insert(cellInfo: CellInfo) {
        cellInfoDao.insert(cellInfo)
    }

    @WorkerThread
    suspend fun getCellInfo(cellId: Int, lac: Int, signalStrength: Int, latitude: Double, longitude: Double): CellInfo? {
        return cellInfoDao.getCellInfo(cellId, lac, signalStrength, latitude, longitude)
    }

    // Function to get all records with a specific cellId
    @WorkerThread
    fun getCellInfoByCellId(cellId: Int): Flow<List<CellInfo>> {
        return cellInfoDao.getCellInfoByCellId(cellId)
    }
}
