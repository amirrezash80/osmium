package com.amirreza.osmiumproject

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [CellInfo::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun cellInfoDao(): CellInfoDao
}
