// CellInfoDao.kt
package com.amirreza.osmiumproject

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface CellInfoDao {

    @Insert
    suspend fun insert(cellInfo: CellInfo)

    @Query("SELECT * FROM cell_info")
    fun getAll(): Flow<List<CellInfo>>
}
