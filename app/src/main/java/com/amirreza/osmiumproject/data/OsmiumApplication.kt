package com.amirreza.osmiumproject.data

import android.app.Application
import androidx.room.Room
import com.amirreza.osmiumproject.data.database.AppDatabase
import com.amirreza.osmiumproject.data.repository.CellInfoRepository

class OsmiumApplication : Application() {
    val database by lazy {
        Room.databaseBuilder(this, AppDatabase::class.java, "osmium_database").build()
    }
    val repository by lazy {
        CellInfoRepository(database.cellInfoDao())
    }
}
