package com.amirreza.osmiumproject

import androidx.annotation.WorkerThread
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
}
