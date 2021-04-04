package kek.enxy.plantwriter.di

import kek.enxy.data.mifare.MifareDataProviderImpl
import kek.enxy.data.readwrite.ReadWriteDataSourceImpl
import kek.enxy.domain.read.ReadDumpUseCase
import kek.enxy.domain.read.ReadDumpUseCaseImpl
import kek.enxy.domain.write.WriteDumpUseCase
import kek.enxy.domain.write.WriteDumpUseCaseImpl
import org.koin.dsl.module

internal val domainModule = module {
    single { MifareDataProviderImpl() }
    single { ReadWriteDataSourceImpl() }
    factory<WriteDumpUseCase> { WriteDumpUseCaseImpl(get(), get()) }
    factory<ReadDumpUseCase> { ReadDumpUseCaseImpl(get()) }
}
