package com.amirreza.osmiumproject

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface CellInfoDao {

    @Query("SELECT * FROM cell_info")
    fun getAllCellInfo(): Flow<List<CellInfo>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(cellInfo: CellInfo)

    @Query("SELECT * FROM cell_info WHERE cellId = :cellId AND lac = :lac AND signalStrength = :signalStrength AND latitude = :latitude AND longitude = :longitude LIMIT 1")
    suspend fun getCellInfo(cellId: Int, lac: Int, signalStrength: Int, latitude: Double, longitude: Double): CellInfo?
}
