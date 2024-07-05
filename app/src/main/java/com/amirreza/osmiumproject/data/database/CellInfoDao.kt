package com.amirreza.osmiumproject.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.amirreza.osmiumproject.ui.model.CellInfo
import kotlinx.coroutines.flow.Flow

@Dao
interface CellInfoDao {

    @Query("SELECT * FROM cell_info")
    fun getAllCellInfo(): Flow<List<CellInfo>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(cellInfo: CellInfo)

    @Query("SELECT * FROM cell_info WHERE cellId = :cellId AND lac = :lac AND signalStrength = :signalStrength AND latitude = :latitude AND longitude = :longitude LIMIT 1")
    suspend fun getCellInfo(cellId: Int, lac: Int, signalStrength: Int, latitude: Double, longitude: Double): CellInfo?

    // Query to get all records with a specific cellId
    @Query("SELECT * FROM cell_info WHERE cellId = :cellId")
    fun getCellInfoByCellId(cellId: Int): Flow<List<CellInfo>>
}
