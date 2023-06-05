package fr.skyle.scanny.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import fr.skyle.scanny.data.vo.BarcodeData
import kotlinx.coroutines.flow.Flow

@Dao
interface BarcodeDao {

    // Insert

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(barcodeData: BarcodeData)

    // Read

    @Query("SELECT * FROM BarcodeData WHERE id = :id")
    fun watchBarcode(id: Long): Flow<BarcodeData>

    @Query("SELECT * FROM BarcodeData")
    fun watchBarcodes(): Flow<List<BarcodeData>>
}