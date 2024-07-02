import androidx.lifecycle.*
import kotlinx.coroutines.launch

class CellInfoViewModel(private val repository: CellInfoRepository) : ViewModel() {

    val allCellInfo: LiveData<List<CellInfo>> = repository.getAll().asLiveData()

    fun insert(cellInfo: CellInfo) = viewModelScope.launch {
        repository.insert(cellInfo)
    }
}

class CellInfoViewModelFactory(private val repository: CellInfoRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CellInfoViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return CellInfoViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
