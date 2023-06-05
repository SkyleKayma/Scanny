package fr.skyle.scanny.data.vo

import androidx.room.Entity
import androidx.room.PrimaryKey
import fr.skyle.scanny.data.enums.BarcodeFormat
import fr.skyle.scanny.data.enums.BarcodeType

@Entity
data class BarcodeData(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val type: BarcodeType,
    val format: BarcodeFormat?,
    val scanDate: Long,
    val content: String?
)