package org.pop.pip.db

import androidx.room.*
import org.pop.pip.aur.AurResult

@Dao
interface HistoryDao {
    @Query("SELECT * FROM history_table") fun getAll(): List<AurResult>

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
@Database(entities = [AurResult::class], version = 1)
abstract class HistroryDataBase : RoomDatabase() {
    abstract fun userHistory(): HistoryDao
}
