package fr.skyle.scanny.data.repository

import fr.skyle.scanny.data.dao.BarcodeDao
import fr.skyle.scanny.data.vo.BarcodeData
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class BarcodeDataRepository @Inject constructor(
    private val barcodeDao: BarcodeDao
) {

    // DB

    suspend fun insert(barcodeData: BarcodeData) {
        barcodeDao.insert(barcodeData)
    }

    fun watchBarcode(id: Long): Flow<BarcodeData> =
        barcodeDao.watchBarcode(id)

    fun watchBarcodes(): Flow<List<BarcodeData>> =
        barcodeDao.watchBarcodes()
}