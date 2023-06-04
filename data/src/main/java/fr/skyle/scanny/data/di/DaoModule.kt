package fr.skyle.scanny.data.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import fr.skyle.scanny.data.dao.BarcodeDao
import fr.skyle.scanny.data.db.SCDb

@Module
@InstallIn(SingletonComponent::class)
object DaoModule {

    @Provides
    fun barcodeDao(prySystemDb: SCDb): BarcodeDao =
        prySystemDb.barcodeDao()
}