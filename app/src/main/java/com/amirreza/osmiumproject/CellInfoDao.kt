import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface CellInfoDao {
    @Insert
    suspend fun insert(cellInfo: CellInfo)

    @Query("SELECT * FROM CellInfo")
    fun getAll(): Flow<List<CellInfo>> // تغییر به Flow
}
