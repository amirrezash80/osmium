// AppDatabase.kt
package com.amirreza.osmiumproject

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [CellInfo::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun cellInfoDao(): CellInfoDao
}
