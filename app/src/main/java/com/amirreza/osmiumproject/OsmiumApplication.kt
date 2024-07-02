import android.app.Application
import androidx.room.Room

class OsmiumApplication : Application() {
    val database by lazy {
        Room.databaseBuilder(this, AppDatabase::class.java, "osmium_database").build()
    }
    val repository by lazy {
        CellInfoRepository(database.cellInfoDao())
    }
}
