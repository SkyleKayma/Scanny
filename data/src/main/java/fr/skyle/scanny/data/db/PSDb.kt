package fr.skyle.scanny.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import fr.skyle.scanny.data.dao.BarcodeDao
import fr.skyle.scanny.data.vo.BarcodeData

@Database(
    entities = [
        BarcodeData::class
    ],
    version = 2
)
abstract class SCDb : RoomDatabase() {

    abstract fun barcodeDao(): BarcodeDao

    companion object {
        fun databaseBuilder(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                SCDb::class.java,
                DATABASE_NAME
            ).fallbackToDestructiveMigration()

        private const val DATABASE_NAME = "scanny_database.db"
    }
}
