package com.amirreza.osmiumproject.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.amirreza.osmiumproject.ui.model.CellInfo

@Database(entities = [CellInfo::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun cellInfoDao(): CellInfoDao
}
