package kek.enxy.plantwriter.di

import kek.enxy.data.db.AppDatabase
import kek.enxy.data.db.AppDatabaseFactory
import kek.enxy.data.dumps.DumpsDataSource
import kek.enxy.data.dumps.DumpsDataSourceImpl
import kek.enxy.data.mifare.MifareDataProvider
import kek.enxy.data.mifare.MifareDataProviderImpl
import kek.enxy.data.readwrite.ReadWriteDataSource
import kek.enxy.data.readwrite.ReadWriteDataSourceImpl
import kek.enxy.data.settings.AppSettingsDataSource
import kek.enxy.data.settings.AppSettingsDataSourceImpl
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

internal val databaseModule = module {
    single { AppDatabaseFactory.create(androidContext()) }
    single { get<AppDatabase>().dumpsDao() }
}

internal val dataModule = module {
    factory<MifareDataProvider> { MifareDataProviderImpl() }
    factory<ReadWriteDataSource> { ReadWriteDataSourceImpl() }
    factory<DumpsDataSource> { DumpsDataSourceImpl(get()) }
    factory<AppSettingsDataSource> { AppSettingsDataSourceImpl() }
}

val dataModules = listOf(databaseModule, dataModule)
