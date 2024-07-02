import androidx.annotation.WorkerThread
import kotlinx.coroutines.flow.Flow

class CellInfoRepository(private val cellInfoDao: CellInfoDao) {

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insert(cellInfo: CellInfo) {
        cellInfoDao.insert(cellInfo)
    }

    fun getAll(): Flow<List<CellInfo>> = cellInfoDao.getAll()
}
