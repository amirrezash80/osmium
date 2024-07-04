package com.amirreza.osmiumproject

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cell_info")
data class CellInfo(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val cellId: Int,
    val lac: Int,
    val signalStrength: Int,
    val latitude: Double,
    val longitude: Double
)
