package kek.enxy.plantwriter.di

import kek.enxy.data.mifare.MifareDataProvider
import kek.enxy.data.mifare.MifareDataProviderImpl
import kek.enxy.data.readwrite.ReadWriteDataSource
import kek.enxy.data.readwrite.ReadWriteDataSourceImpl
import org.koin.dsl.module

internal val dataModule = module {
    factory<MifareDataProvider> { MifareDataProviderImpl() }
    factory<ReadWriteDataSource> { ReadWriteDataSourceImpl() }
}
