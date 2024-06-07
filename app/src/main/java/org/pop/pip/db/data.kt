package org.pop.pip.db

import androidx.lifecycle.ViewModel
import androidx.room.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import org.pop.pip.aur.AurResult

@Dao
interface HistoryDao {
    @Query("SELECT * FROM history_table ORDER BY name asc") fun getAll(): Flow<List<AurResult>>

    @Insert fun insertHistory(vararg history: AurResult)
}

class ListConvertor {
    @TypeConverter
    fun fromListString(list: List<String>): String {
        return list.joinToString(",")
    }

    @TypeConverter
    fun toListString(data: String): List<String> {
        return listOf(*data.split(",").map { it.toString() }.toTypedArray())
    }
}

@TypeConverters(ListConvertor::class)
@Database(entities = [AurResult::class], version = 1, exportSchema = false)
abstract class HistoryDataBase : RoomDatabase() {
    abstract fun userHistory(): HistoryDao
}

class HistoryViewModel(private val dao: HistoryDao) : ViewModel() {
    fun getAllRecords(): Flow<List<AurResult>> {
        return dao.getAll()
    }
    fun insertHistory(history: AurResult) {
        CoroutineScope(Dispatchers.IO).launch { dao.insertHistory(history) }
    }
}
